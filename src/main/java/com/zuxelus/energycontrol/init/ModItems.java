package com.zuxelus.energycontrol.init;

import com.zuxelus.energycontrol.EnergyControl;
import com.zuxelus.energycontrol.blocks.*;
import com.zuxelus.energycontrol.crossmod.CrossModLoader;
import com.zuxelus.energycontrol.crossmod.ic2.CrossIC2.IC2Type;
import com.zuxelus.energycontrol.items.ItemAFSU;
import com.zuxelus.energycontrol.items.ItemAFSUUpgradeKit;
import com.zuxelus.energycontrol.items.ItemDigitalThermometer;
import com.zuxelus.energycontrol.items.ItemLight;
import com.zuxelus.energycontrol.items.ItemNanoBowIC2;
import com.zuxelus.energycontrol.items.ItemNanoBowTR;
import com.zuxelus.energycontrol.items.ItemPortablePanel;
import com.zuxelus.energycontrol.items.ItemThermometer;
import com.zuxelus.energycontrol.items.ItemUpgrade;
import com.zuxelus.energycontrol.items.cards.ItemCardHolder;
import com.zuxelus.energycontrol.items.cards.ItemCardMain;
import com.zuxelus.energycontrol.items.kits.ItemKitMain;
import com.zuxelus.energycontrol.recipes.NanoBowRecipe;
import com.zuxelus.energycontrol.recipes.NanoBowRecipeTR;
import com.zuxelus.energycontrol.recipes.StorageArrayRecipe;
import com.zuxelus.energycontrol.tileentities.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class ModItems {
	public static Block blockLight;
	public static Block blockHowlerAlarm;
	public static Block blockIndustrialAlarm;
	public static Block blockThermalMonitor;
	public static Block blockInfoPanel;
	public static Block blockInfoPanelExtender;
	public static Block blockInfoPanelAdvanced;
	public static Block blockInfoPanelAdvancedExtender;
	public static Block blockRangeTrigger;
	public static Block blockRemoteThermo;
	public static Block blockAverageCounter;
	public static Block blockEnergyCounter;
	public static Block blockKitAssembler;
	public static Block blockAfsu;
	public static Block blockIc2Cable;
	public static Block blockSeedAnalyzer;
	public static Block blockSeedLibrary;
	public static Item itemKit;
	public static Item itemCard;
	public static Item itemUpgrade;
	public static Item itemThermometer;
	public static Item itemThermometerDigital;
	public static Item itemPortablePanel;
	public static Item itemCardHolder;
	public static Item itemNanoBow;
	public static Item itemAFB;
	public static Item itemAFSUUpgradeKit;

	@SubscribeEvent
	public static void onBlockRegistry(Register<Block> event) {
		blockLight = register(event, new BlockLight(), "block_light");
		blockHowlerAlarm = register(event, new HowlerAlarm(), "howler_alarm");
		blockIndustrialAlarm = register(event, new IndustrialAlarm(), "industrial_alarm");
		if (Loader.isModLoaded("ic2"))
			blockThermalMonitor = register(event, new ThermalMonitor(), "thermal_monitor");
		blockInfoPanel = register(event, new InfoPanel(), TileEntityInfoPanel.NAME);
		blockInfoPanelExtender = register(event, new InfoPanelExtender(), "info_panel_extender");
		blockInfoPanelAdvanced = register(event, new AdvancedInfoPanel(), TileEntityAdvancedInfoPanel.NAME);
		blockInfoPanelAdvancedExtender = register(event, new AdvancedInfoPanelExtender(), "info_panel_advanced_extender");
		blockRangeTrigger = register(event, new RangeTrigger(), "range_trigger");
		if (Loader.isModLoaded("ic2"))
			blockRemoteThermo = register(event, new RemoteThermo(), "remote_thermo");
		blockAverageCounter = register(event, new AverageCounter(), "average_counter");
		blockEnergyCounter = register(event, new EnergyCounter(), "energy_counter");
		blockKitAssembler = register(event, new KitAssembler(), "kit_assembler");
		if (CrossModLoader.ic2.getProfile() == 0) {
			blockAfsu = register(event, new AFSU(), "afsu");
			//blockIc2Cable = register(event, new IC2Cable(), "ic2_cable");
		}
		if (Loader.isModLoaded("ic2")) {
			blockSeedAnalyzer = register(event, new SeedAnalyzer(), "seed_analyzer");
			blockSeedLibrary = register(event, new SeedLibrary(), "seed_library");
		}
	}

	@SubscribeEvent
	public static void onItemRegistry(Register<Item> event) {
		event.getRegistry().register(new ItemLight(blockLight).setRegistryName("block_light"));
		event.getRegistry().register(new ItemBlock(blockHowlerAlarm).setRegistryName("howler_alarm"));
		event.getRegistry().register(new ItemBlock(blockIndustrialAlarm).setRegistryName("industrial_alarm"));
		if (Loader.isModLoaded("ic2"))
			event.getRegistry().register(new ItemBlock(blockThermalMonitor).setRegistryName("thermal_monitor"));
		event.getRegistry().register(new ItemBlock(blockInfoPanel).setRegistryName(TileEntityInfoPanel.NAME));
		event.getRegistry().register(new ItemBlock(blockInfoPanelExtender).setRegistryName("info_panel_extender"));
		event.getRegistry().register(new ItemBlock(blockInfoPanelAdvanced).setRegistryName(TileEntityAdvancedInfoPanel.NAME));
		event.getRegistry().register(new ItemBlock(blockInfoPanelAdvancedExtender).setRegistryName("info_panel_advanced_extender"));
		event.getRegistry().register(new ItemBlock(blockRangeTrigger).setRegistryName("range_trigger"));
		if (Loader.isModLoaded("ic2"))
			event.getRegistry().register(new ItemBlock(blockRemoteThermo).setRegistryName("remote_thermo"));
		event.getRegistry().register(new ItemBlock(blockAverageCounter).setRegistryName("average_counter"));
		event.getRegistry().register(new ItemBlock(blockEnergyCounter).setRegistryName("energy_counter"));
		event.getRegistry().register(new ItemBlock(blockKitAssembler).setRegistryName("kit_assembler"));
		if (CrossModLoader.ic2.getProfile() == 0) {
			event.getRegistry().register(new ItemAFSU(blockAfsu).setRegistryName("afsu"));
			//event.getRegistry().register(new ItemBlock(blockIc2Cable).setRegistryName("ic2_cable"));
		}
		if (Loader.isModLoaded("ic2")) {
			event.getRegistry().register(new ItemBlock(blockSeedAnalyzer).setRegistryName("seed_analyzer"));
			event.getRegistry().register(new ItemBlock(blockSeedLibrary).setRegistryName("seed_library"));
		}

		itemUpgrade = register(event, new ItemUpgrade(), "item_upgrade");
		if (Loader.isModLoaded("ic2")) {
			itemThermometer = register(event, new ItemThermometer(), "thermometer");
			itemThermometerDigital = register(event, new ItemDigitalThermometer(), "thermometer_digital");
		}

		if (Loader.isModLoaded("ic2"))
			itemNanoBow = new ItemNanoBowIC2();
		else if (Loader.isModLoaded("techreborn"))
			itemNanoBow = new ItemNanoBowTR();
		if (itemNanoBow != null)
			register(event, itemNanoBow, "nano_bow");

		if (CrossModLoader.ic2.getProfile() == 0) {
			itemAFB = register(event, CrossModLoader.ic2.getItem("afb"), "afb");
			itemAFSUUpgradeKit = register(event, new ItemAFSUUpgradeKit(), "afsu_upgrade_kit");
		}

		itemPortablePanel = register(event, new ItemPortablePanel(), "portable_panel");

		itemKit = new ItemKitMain();
		((ItemKitMain) itemKit).registerKits();
		register(event, itemKit, "item_kit");

		itemCard = new ItemCardMain();
		((ItemCardMain) itemCard).registerCards();
		register(event, itemCard, "item_card");

		itemCardHolder = register(event, new ItemCardHolder(), "card_holder");
	}

	public static Block register(Register<Block> event, Block block, String name) {
		block.setUnlocalizedName(name);
		block.setRegistryName(name);
		event.getRegistry().register(block);
		return block;
	}

	public static Item register(Register<Item> event, Item item, String name) {
		item.setUnlocalizedName(name);
		item.setRegistryName(name);
		event.getRegistry().register(item);
		return item;
	}

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		registerBlockModel(ModItems.blockLight, BlockLight.DAMAGE_WHITE_OFF, "lamp0");
		registerBlockModel(ModItems.blockLight, BlockLight.DAMAGE_WHITE_ON, "lamp1");
		registerBlockModel(ModItems.blockLight, BlockLight.DAMAGE_ORANGE_OFF, "lamp2");
		registerBlockModel(ModItems.blockLight, BlockLight.DAMAGE_ORANGE_ON, "lamp3");

		registerBlockModel(ModItems.blockHowlerAlarm, 0, "howler_alarm");
		registerBlockModel(ModItems.blockIndustrialAlarm, 0, "industrial_alarm");
		if (Loader.isModLoaded("ic2")) {
			registerBlockModel(ModItems.blockThermalMonitor, 0, "thermal_monitor");
			registerBlockModel(ModItems.blockRemoteThermo, 0, "remote_thermo");
		}
		registerBlockModel(ModItems.blockInfoPanel, 0, TileEntityInfoPanel.NAME);
		registerBlockModel(ModItems.blockInfoPanelExtender, 0, "info_panel_extender");
		registerBlockModel(ModItems.blockInfoPanelAdvanced, 0, TileEntityAdvancedInfoPanel.NAME);
		registerBlockModel(ModItems.blockInfoPanelAdvancedExtender, 0, "info_panel_advanced_extender");
		registerBlockModel(ModItems.blockRangeTrigger, 0, "range_trigger");

		registerBlockModel(ModItems.blockAverageCounter, 0, "average_counter");
		registerBlockModel(ModItems.blockEnergyCounter, 0, "energy_counter");
		registerBlockModel(ModItems.blockKitAssembler, 0, "kit_assembler");
		if (CrossModLoader.ic2.getProfile() == 0) {
			registerBlockModel(ModItems.blockAfsu, 0, "afsu");

			/*((IC2Cable) blockIc2Cable).initModel();
			registerBlockModel(ModItems.blockIc2Cable, 0, "ic2_cable");*/
		}
		registerBlockModel(blockSeedAnalyzer, 0, "seed_analyzer");
		registerBlockModel(blockSeedLibrary, 0, "seed_library");

		ItemKitMain.registerModels();
		ItemKitMain.registerExtendedModels();
		ItemCardMain.registerModels();
		ItemCardMain.registerExtendedModels();

		registerItemModel(ModItems.itemUpgrade, ItemUpgrade.DAMAGE_RANGE, "upgrade_range");
		registerItemModel(ModItems.itemUpgrade, ItemUpgrade.DAMAGE_COLOR, "upgrade_color");
		registerItemModel(ModItems.itemUpgrade, ItemUpgrade.DAMAGE_TOUCH, "upgrade_touch");
		if (Loader.isModLoaded("ic2")) {
			registerItemModel(ModItems.itemThermometer, 0, "thermometer");
			registerItemModel(ModItems.itemThermometerDigital, 0, "thermometer_digital");
		}
		registerItemModel(ModItems.itemPortablePanel, 0, "portable_panel");
		registerItemModel(ModItems.itemCardHolder, 0, "card_holder");
		if (ModItems.itemNanoBow != null)
			registerItemModel(ModItems.itemNanoBow, 0, "nano_bow");
		if (CrossModLoader.ic2.getProfile() == 0) {
			registerItemModel(ModItems.itemAFB, 0, "afb");
			registerItemModel(ModItems.itemAFSUUpgradeKit, 0, "afsu_upgrade_kit");
		}
	}

	public static void registerItemModel(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(EnergyControl.MODID + ":" + name, "inventory"));
	}

	public static void registerExternalItemModel(Item item, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
	}

	private static void registerBlockModel(Block block, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(EnergyControl.MODID + ":" + name, "inventory"));
	}

	public static void registerTileEntities() { // TODO Change to event
		GameRegistry.registerTileEntity(TileEntityHowlerAlarm.class, EnergyControl.MODID + ":howler_alarm");
		GameRegistry.registerTileEntity(TileEntityIndustrialAlarm.class, EnergyControl.MODID + ":industrial_alarm");
		if (Loader.isModLoaded("ic2")) {
			GameRegistry.registerTileEntity(TileEntityThermo.class, EnergyControl.MODID + ":thermo");
			GameRegistry.registerTileEntity(TileEntityRemoteThermo.class, EnergyControl.MODID + ":remote_thermo");
		}
		GameRegistry.registerTileEntity(TileEntityInfoPanel.class, EnergyControl.MODID + ":" + TileEntityInfoPanel.NAME);
		GameRegistry.registerTileEntity(TileEntityInfoPanelExtender.class, EnergyControl.MODID + ":info_panel_extender");
		GameRegistry.registerTileEntity(TileEntityAdvancedInfoPanel.class, EnergyControl.MODID + ":" + TileEntityAdvancedInfoPanel.NAME);
		GameRegistry.registerTileEntity(TileEntityAdvancedInfoPanelExtender.class, EnergyControl.MODID + ":info_panel_advanced_extender");
		GameRegistry.registerTileEntity(TileEntityRangeTrigger.class, EnergyControl.MODID + ":range_trigger");
		GameRegistry.registerTileEntity(TileEntityAverageCounter.class, EnergyControl.MODID + ":average_counter");
		GameRegistry.registerTileEntity(TileEntityEnergyCounter.class, EnergyControl.MODID + ":energy_counter");
		GameRegistry.registerTileEntity(TileEntityKitAssembler.class, EnergyControl.MODID + ":kit_assembler");
		if (CrossModLoader.ic2.getProfile() == 0)
			GameRegistry.registerTileEntity(TileEntityAFSU.class, EnergyControl.MODID + ":afsu");
		GameRegistry.registerTileEntity(TileEntitySeedAnalyzer.class, EnergyControl.MODID + ":seed_analyzer");
		GameRegistry.registerTileEntity(TileEntitySeedLibrary.class, EnergyControl.MODID + ":seed_library");
	}

	@SubscribeEvent
	public static void registerRecipes(Register<IRecipe> event) {
		event.getRegistry().register(new StorageArrayRecipe().setRegistryName("array_card_recipe"));
		if (Loader.isModLoaded("ic2"))
			event.getRegistry().register(new NanoBowRecipe().setRegistryName("nano_bow_recipe"));
		if (Loader.isModLoaded("techreborn"))
			event.getRegistry().register(new NanoBowRecipeTR().setRegistryName("nano_bow_recipe_tr"));
	}
}
