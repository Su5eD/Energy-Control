package com.zuxelus.energycontrol.blocks;

import java.util.Collections;
import java.util.List;

import com.zuxelus.energycontrol.EnergyControl;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import com.zuxelus.energycontrol.tileentities.TileEntityAFSU;
import com.zuxelus.energycontrol.tileentities.TileEntityFacing;
import com.zuxelus.energycontrol.tileentities.TileEntityInventory;
import com.zuxelus.energycontrol.tileentities.TileEntityRangeTrigger;

import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AFSU extends FacingBlock implements ITileEntityProvider, IWrenchable {

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		TileEntityAFSU te = new TileEntityAFSU();
		te.setFacing(meta);
		return te;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote)
			player.openGui(EnergyControl.instance, BlockDamages.DAMAGE_AFSU, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (placer.rotationPitch >= 65)
			return getDefaultState().withProperty(FACING, EnumFacing.UP);
		if (placer.rotationPitch <= -65)
			return getDefaultState().withProperty(FACING, EnumFacing.DOWN);
		switch (MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) {
		case 0:
			return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
		case 1:
			return getDefaultState().withProperty(FACING, EnumFacing.EAST);
		case 2:
			return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
		case 3:
			return getDefaultState().withProperty(FACING, EnumFacing.WEST);
		}		
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null)
			return;
		double energy = tag.getDouble("energy");
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof TileEntityAFSU) || energy == 0)
			return;
		((TileEntityAFSU) te).setEnergy(energy);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!world.isRemote)
			world.notifyBlockUpdate(pos, state, state, 2);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityInventory)
			((TileEntityInventory)te).dropItems(world, pos);
		super.breakBlock(world, pos, state);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(CrossModLoader.ic2.getItem("mfsu"));
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return getStrongPower(blockState, blockAccess, pos, side);
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileEntity te = blockAccess.getTileEntity(pos);
		if (!(te instanceof TileEntityAFSU))
			return 0;
		return ((TileEntityAFSU) te).getPowered() ? 15 : 0;
	}

	//IWrenchable
	@Override
	public EnumFacing getFacing(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityFacing)
			return ((TileEntityFacing) te).getFacing();
		return EnumFacing.DOWN;
	}

	@Override
	public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityFacing) {
			((TileEntityFacing) te).setFacing(newDirection.getIndex());
			world.setBlockState(pos, getDefaultState().withProperty(FACING, newDirection));
			return true;
		}
		return false;
	}

	@Override
	public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
		return true;
	}

	@Override
	public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te, EntityPlayer player, int fortune) {
		if (!(te instanceof TileEntityAFSU))
			return Collections.emptyList();
		List<ItemStack> list = ((TileEntityInventory) te).getDrops(fortune);
		list.add(getStackwithEnergy(((TileEntityAFSU) te).getEnergy() * 0.8D));
		return list;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(getStackwithEnergy(0));
		items.add(getStackwithEnergy(TileEntityAFSU.CAPACITY));
	}

	private ItemStack getStackwithEnergy(double energy) {
		ItemStack stack = new ItemStack(this);
		NBTTagCompound tag = new NBTTagCompound();
		stack.setTagCompound(tag);
		tag.setDouble("energy", energy);
		return stack;
	}
}