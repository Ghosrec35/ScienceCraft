package net.sciencecraft.src;

import java.util.HashMap;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import net.minecraft.src.Achievement;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.sciencecraft.src.blocks.*;
import net.sciencecraft.src.client.ClientTickHandler;
import net.sciencecraft.src.items.ItemADP;
import net.sciencecraft.src.items.ItemATP;
import net.sciencecraft.src.items.ItemDeoxyriboNucleicAcid;
import net.sciencecraft.src.items.ItemFAD;
import net.sciencecraft.src.items.ItemFADH;
import net.sciencecraft.src.items.ItemLipid;
import net.sciencecraft.src.items.ItemLipidComponents;
import net.sciencecraft.src.items.ItemNAD;
import net.sciencecraft.src.items.ItemNADH;
import net.sciencecraft.src.items.ItemNADP;
import net.sciencecraft.src.items.ItemNADPH;
import net.sciencecraft.src.items.ItemRiboNucleicAcid;
import net.sciencecraft.src.items.ItemSalt;
import net.sciencecraft.src.items.ItemSaltedFood;
import net.sciencecraft.src.items.ItemSoda;
import net.sciencecraft.src.items.ItemSugar;
import net.sciencecraft.src.machineblocks.catalyzer.BlockCatalyzer;
import net.sciencecraft.src.machineblocks.incubator.BlockElectricIncubator;
import net.sciencecraft.src.machineblocks.incubator.BlockIncubator;
import net.sciencecraft.src.machineblocks.refiner.BlockRefiner;
import net.sciencecraft.src.machineblocks.refiner.TileEntityRefiner;
import net.sciencecraft.src.machineblocks.teleporter.TeleportationManager;
import net.sciencecraft.src.machineblocks.teleporter.TileEntityTeleporter;

public class SCRegistrationHandler 
{
	private static final SCRegistrationHandler INSTANCE = new SCRegistrationHandler();
	
	public static HashMap<Integer, String> SCAchs = new HashMap();
	public static HashMap<Integer, String> SCAchNames = new HashMap();
	public static HashMap<Integer, String> SCAchDescs = new HashMap();
	
	private static int availableBlockID = 100;
	private static int availableItemID = 4096;
	
	public static TeleportationManager teleporterRegistry = TeleportationManager.getManagerInstance();
	
	public static final SCRegistrationHandler instance()
	{
		return INSTANCE;
	}
	
	private static int getNextAvailableBlockID()
	{
		if(availableBlockID < Block.blocksList.length)
		{
			if(Block.blocksList[availableBlockID] != null)
			{
				availableBlockID++;
				return getNextAvailableBlockID();
			}
			else
			{
				return availableBlockID;
			}
		}
		else
		{
			throw new IllegalArgumentException("Unable to get the next available Block ID as there are none left!");	
		}
	}
	
	private static int getNextAvailableItemID()
	{

			if(Item.itemsList[availableItemID] != null)
			{
				availableItemID++;
				return getNextAvailableItemID();
			}
			else
			{
				return availableItemID;
			}
	}
	

	//Tool Enum's
	private static EnumToolMaterial toolBERYL = EnumHelper.addToolMaterial("BERYL", 2, 1250, 11F, 2, 21);
	private static EnumToolMaterial toolBORON = EnumHelper.addToolMaterial("BORON", 3, 1500, 12F, 3, 28);
	private static EnumToolMaterial toolALUMINIUM = EnumHelper.addToolMaterial("ALUMINIUM", 1, 400, 5F, 1, 14);
	private static EnumToolMaterial toolTITANIUM = EnumHelper.addToolMaterial("TITANIUM", 2, 1000, 10F, 2, 20);
	private static EnumToolMaterial toolCHROMIUM = EnumHelper.addToolMaterial("CHROMIUM", 2, 1300, 12F, 3, 26);
	private static EnumToolMaterial toolCOBALT = EnumHelper.addToolMaterial("COBALT", 2, 700, 7F, 2, 7);
	private static EnumToolMaterial toolNICKEL = EnumHelper.addToolMaterial("NICKEL", 1, 450, 5F, 1, 4);
	private static EnumToolMaterial toolCOPPER = EnumHelper.addToolMaterial("COPPER", 1, 425, 4F, 1, 4);
	private static EnumToolMaterial toolZIRCONIUM = EnumHelper.addToolMaterial("ZIRCONIUM", 2, 750, 8F, 2, 7);
	private static EnumToolMaterial toolSILVER = EnumHelper.addToolMaterial("SILVER", 0, 200, 2F, 1, 2);
	private static EnumToolMaterial toolTIN = EnumHelper.addToolMaterial("TIN", 0, 80, 8F, 1, 1);
	private static EnumToolMaterial toolPLATINUM = EnumHelper.addToolMaterial("PLATINUM", 2, 600, 8F, 2, 6);
	private static EnumToolMaterial toolLEAD = EnumHelper.addToolMaterial("LEAD", 0, 80, 2F, 1, 1);
	private static EnumToolMaterial toolCINNABAR = EnumHelper.addToolMaterial("CINNABAR", 1, 350, 4F, 1, 3);
	private static EnumToolMaterial toolBISMUTH = EnumHelper.addToolMaterial("BISMUTH", 1, 350, 4F, 1, 3);
	private static EnumToolMaterial toolURANIUM = EnumHelper.addToolMaterial("URANIUM", 2, 1000, 12F, 2, 28);
	private static EnumToolMaterial toolPLUTONIUM = EnumHelper.addToolMaterial("PLUTONIUM", 2, 1200, 14F, 3, 30);
	private static EnumToolMaterial toolSTEEL = EnumHelper.addToolMaterial("STEEL", 1, 900, 4F, 1, 3);
	private static EnumToolMaterial toolBRONZE = EnumHelper.addToolMaterial("BRONZE", 1, 300, 4F, 1, 3);
	
	//Armor Enum's
	private static EnumArmorMaterial armorBERYL = EnumHelper.addArmorMaterial("BERYL", 25, new int[] {1, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorBORON = EnumHelper.addArmorMaterial("BORON", 30, new int[] {2, 5, 4, 1}, 12);
	private static EnumArmorMaterial armorALUMINIUM = EnumHelper.addArmorMaterial("ALUMINIUM", 7, new int[] {2, 6, 5, 2}, 9);
	private static EnumArmorMaterial armorTITANIUM = EnumHelper.addArmorMaterial("TITANIUM", 20, new int[] {2, 5, 3, 1}, 25);
	private static EnumArmorMaterial armorCHROMIUM = EnumHelper.addArmorMaterial("CHROMIUM", 26, new int[] {3, 8, 6, 3}, 10);
	private static EnumArmorMaterial armorCOBALT = EnumHelper.addArmorMaterial("COBALT", 13, new int[] {1, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorNICKEL = EnumHelper.addArmorMaterial("NICKEL", 7, new int[] {2, 5, 4, 1}, 12);
	private static EnumArmorMaterial armorCOPPER = EnumHelper.addArmorMaterial("COPPER", 7, new int[] {2, 6, 5, 2}, 9);
	private static EnumArmorMaterial armorZIRCONIUM = EnumHelper.addArmorMaterial("ZIRCONIUM", 7, new int[] {2, 5, 3, 1}, 25);
	private static EnumArmorMaterial armorSILVER = EnumHelper.addArmorMaterial("SILVER", 5, new int[] {3, 8, 6, 3}, 10);
	private static EnumArmorMaterial armorTIN = EnumHelper.addArmorMaterial("TIN", 5, new int[] {1, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorPLATINUM = EnumHelper.addArmorMaterial("PLATINUM", 8, new int[] {2, 5, 4, 1}, 12);
	private static EnumArmorMaterial armorLEAD = EnumHelper.addArmorMaterial("LEAD", 5, new int[] {2, 6, 5, 2}, 9);
	private static EnumArmorMaterial armorCINNABAR = EnumHelper.addArmorMaterial("CINNABAR", 7, new int[] {2, 5, 3, 1}, 25);
	private static EnumArmorMaterial armorBISMUTH = EnumHelper.addArmorMaterial("BISMUTH", 8, new int[] {3, 8, 6, 3}, 10);
	private static EnumArmorMaterial armorURANIUM = EnumHelper.addArmorMaterial("URANIUM", 20, new int[] {3, 8, 6, 3}, 10);
	private static EnumArmorMaterial armorPLUTONIUM = EnumHelper.addArmorMaterial("PLUTONIUM", 5, new int[] {1, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorSTEEL = EnumHelper.addArmorMaterial("STEEL", 5, new int[] {1, 3, 2, 1}, 15);
	private static EnumArmorMaterial armorBRONZE = EnumHelper.addArmorMaterial("BRONZE", 8, new int[] {2, 5, 4, 1}, 12);
	
	
	//Gas Blocks
	public static final Block Hydrogen = (new BlockHydrogen(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Hydrogen Gas");
	public static final Block Helium = (new BlockHelium(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Helium Gas");
	public static final Block Nitrogen = (new BlockNitrogen(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Nitrogen Gas");
	public static final Block Oxygen = (new BlockOxygen(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Oxygen Gas");
	public static final Block Fluorine = (new BlockFlourine(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Flourine Gas");
	public static final Block Neon = (new BlockNeon(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Neon Gas");
	public static final Block Chlorine = (new BlockChlorine(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Chlorine Gas");
	public static final Block Argon = (new BlockArgon(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Argon Gas");
	public static final Block Krypton = (new BlockKrypton(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Krypton Gas");
	public static final Block Xenon = (new BlockXenon(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Xenon Gas");
	public static final Block Radon = (new BlockRadon(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(1000).setBlockName("Radon Gas");
	
	//Solid Blocks
	public static final Block lithiumOre = (new BlockLithiumOre(getNextAvailableBlockID(), 2)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Lithium Ore");
	public static final Block berylOre = (new BlockBerylOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Beryl Ore");
	public static final Block boronOre = (new BlockBoronOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Boron Ore");
	public static final Block graphiteOre = (new BlockCarbonOre(getNextAvailableBlockID(), 1)).setHardness(0.1F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Carbon Ore");
	public static final Block sodiumOre = (new BlockSodiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Sodium Ore");
	public static final Block magnesiumOre = (new BlockMagnesiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Magnesium Ore");
	public static final Block aluminiumOre = (new BlockAluminiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Aluminium Ore");
	public static final Block siliconOre = (new BlockSiliconOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Silicon Ore");
	public static final Block phosphorusOre = (new BlockPhosphorusOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Phosphorus Ore");
	public static final Block sulfurOre = (new BlockSulfurOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Sulfur Ore");
	public static final Block potassiumOre = (new BlockPotassiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Potassium Ore");
	public static final Block calciumOre = (new BlockCalciumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Calcium Ore");
	public static final Block scandiumOre = (new BlockScandiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Scandium Ore");
	public static final Block titaniumOre = (new BlockTitaniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Titanium Ore");
	public static final Block vanadiumOre = (new BlockVanadiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Vanadium Ore");
	public static final Block chromiumOre = (new BlockChromiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Chromium Ore");
	public static final Block manganeseOre = (new BlockManganeseOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Manganese Ore");
	public static final Block cobaltOre = (new BlockCobaltOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Cobalt Ore");
	public static final Block nickelOre = (new BlockNickelOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Nickel Ore");
	public static final Block copperOre = (new BlockCopperOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Copper Ore");
	public static final Block zincOre = (new BlockZincOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Zinc Ore");
	public static final Block galliumOre = (new BlockGalliumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Gallium Ore");
	public static final Block germaniumOre = (new BlockGermaniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Germanium Ore");
	public static final Block arsenicOre = (new BlockArsenicOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Arsenic Ore");
	public static final Block seleniumOre = (new BlockSeleniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200F).setStepSound(Block.soundStoneFootstep).setBlockName("Selenium Ore");
	public static final Block rubidiumOre = (new BlockRubidiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Rubidium Ore");
	public static final Block strontiumOre = (new BlockStrontiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Strontium Ore");
	public static final Block yttriumOre = (new BlockYttriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Yttrium Ore");
	public static final Block zirconiumOre = (new BlockZirconiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Zirconium Ore");
	public static final Block niobiumOre = (new BlockNiobiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Niobium Ore");
	public static final Block molybdenumOre = (new BlockMolybdenumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Molybdenum Ore");
	public static final Block technetiumOre = (new BlockTechnetiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Technetium Ore");
	public static final Block rutheniumOre = (new BlockRutheniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ruthenium Ore");
	public static final Block rhodiumOre = (new BlockRhodiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Rhodium Ore");
	public static final Block palladiumOre = (new BlockPalladiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Palladium Ore");
	public static final Block silverOre = (new BlockSilverOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Silver Ore");
	public static final Block cadmiumOre = (new BlockCadmiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Cadmium Ore");
	public static final Block indiumOre = (new BlockIndiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Indium Ore");
	public static final Block tinOre = (new BlockTinOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Tin Ore");
	public static final Block antimonyOre = (new BlockAntimonyOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Antimony Ore");
	public static final Block telluriumOre = (new BlockTelluriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Tellurium Ore");
	public static final Block iodineOre = (new BlockIodineOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Iodine Ore");
	public static final Block caesiumOre = (new BlockCaesiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Caesium Ore");
	public static final Block bariumOre = (new BlockBariumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Barium Ore");
	public static final Block lanthanumOre = (new BlockLanthanumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Lanthanum Ore");
	public static final Block ceriumOre = (new BlockCeriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Cerium Ore");
	public static final Block praseodymiumOre = (new BlockPraseodymiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Praseodymium Ore");
	public static final Block neodymiumOre = (new BlockNeodymiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Neodymium Ore");
	public static final Block promethiumOre = (new BlockPromethiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Promethium Ore");
	public static final Block samariumOre = (new BlockSamariumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Samarium Ore");
	public static final Block europiumOre = (new BlockEuropiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Europium Ore");
	public static final Block gadoliniumOre = (new BlockGadoliniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Gadolinium Ore");
	public static final Block terbiumOre = (new BlockTerbiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Terbium Ore");
	public static final Block dysprosiumOre = (new BlockDysprosiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Dysprosium Ore");
	public static final Block holmiumOre = (new BlockHolmiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Holmium Ore");
	public static final Block erbiumOre = (new BlockErbiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Erbium Ore");
	public static final Block thuliumOre = (new BlockThuliumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Thulium Ore");
	public static final Block ytterbiumOre = (new BlockYtterbiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ytterbium Ore");
	public static final Block lutetiumOre = (new BlockLutetiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Lutetium Ore");
	public static final Block hafniumOre = (new BlockHafniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Hafnium Ore");
	public static final Block tantalumOre = (new BlockTantalumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Tantalum Ore");
	public static final Block tungstenOre = (new BlockTungstenOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Tungsten Ore");
	public static final Block rheniumOre = (new BlockRheniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Rhenium Ore");
	public static final Block osmiumOre = (new BlockOsmiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Osmium Ore");
	public static final Block iridiumOre = (new BlockIridiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Iridium Ore");
	public static final Block platinumOre = (new BlockPlatinumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Platinum Ore");
	public static final Block thalliumOre = (new BlockThalliumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Thallium Ore");
	public static final Block leadOre = (new BlockLeadOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Lead Ore");
	public static final Block bismuthOre = (new BlockBismuthOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Bismuth Ore");
	public static final Block poloniumOre = (new BlockPoloniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Polonium Ore");
	public static final Block astatineOre = (new BlockAstatineOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Astatine Ore");
	public static final Block actiniumOre = (new BlockActiniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Actinium Ore");
	public static final Block franciumOre = (new BlockFranciumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Francium Ore");
	public static final Block radiumOre = (new BlockRadiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Radium Ore");
	public static final Block thoriumOre = (new BlockThoriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Thorium Ore");
	public static final Block protactiniumOre = (new BlockProtactiniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Protactinium Ore");
	public static final Block uraniumOre = (new BlockUraniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Uranium Ore");
	public static final Block neptuniumOre = (new BlockNeptuniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Neptunium Ore");
	public static final Block plutoniumOre = (new BlockPlutoniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Plutonium Ore");
	public static final Block americiumOre = (new BlockAmericiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Americium Ore");
	public static final Block curiumOre = (new BlockCuriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Curium Ore");
	public static final Block berkeliumOre = (new BlockBerkeliumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Berkelium Ore");
	public static final Block californiumOre = (new BlockCaliforniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Californium Ore");
	public static final Block einsteiniumOre = (new BlockEinsteiniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Einsteinium Ore");
	public static final Block fermiumOre = (new BlockFermiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Fermium Ore");
	public static final Block mendeleviumOre = (new BlockMendeleviumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Mendelevium Ore");
	public static final Block nobeliumOre = (new BlockNobeliumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Nobelium Ore");
	public static final Block lawrenciumOre = (new BlockLawrenciumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Lawrencium Ore");
	public static final Block rutherfordiumOre = (new BlockRutherfordiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Rutherfordium Ore");
	public static final Block dubniumOre = (new BlockDubniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Dubnium Ore");
	public static final Block seaborgiumOre = (new BlockSeaborgiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Seaborgium Ore");
	public static final Block bohriumOre = (new BlockBohriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Bohrium Ore");
	public static final Block hassiumOre = (new BlockHassiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Hassium Ore");
	public static final Block meitneriumOre = (new BlockMeitneriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Meitnerium Ore");
	public static final Block darmstadtiumOre = (new BlockDarmstadiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Darmstadium Ore");
	public static final Block roentgeniumOre = (new BlockRoentgeniumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Roentgenium Ore");
	public static final Block coperniciumOre = (new BlockCoperniciumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Copernicium Ore");
	public static final Block ununtriumOre = (new BlockUnuntriumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundClothFootstep).setBlockName("Ununtrium Ore");
	public static final Block ununquadiumOre = (new BlockUnunquadiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ununquadium Ore");
	public static final Block ununpentiumOre = (new BlockUnunpentiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ununpentium Ore");
	public static final Block ununhexiumOre = (new BlockUnunhexiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ununhexium Ore");
	public static final Block ununseptiumOre = (new BlockUnunseptiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ununseptium Ore");
	public static final Block ununoctiumOre = (new BlockUnunoctiumOre(getNextAvailableBlockID(), 1)).setHardness(2F).setResistance(200).setStepSound(Block.soundStoneFootstep).setBlockName("Ununoctium Ore");
	
	
	//Liquid Blocks 
	public static final Block liquidBromineFlowing = (new BlockBromineFlowing(getNextAvailableBlockID(), Material.water)).setHardness(100F).setLightOpacity(255).setBlockName("Liquid Bromine");
	public static final Block liquidBromineStationary = (new BlockBromineStationary(getNextAvailableBlockID(), Material.water)).setHardness(100F).setLightOpacity(255).setBlockName("Liquid Bromine Stationary");
	public static final Block liquidMercuryFlowing = (new BlockBromineFlowing(getNextAvailableBlockID(), Material.water)).setHardness(100F).setLightOpacity(255).setBlockName("Liquid Mercury");
	public static final Block liquidMercuryStationary = (new BlockBromineStationary(getNextAvailableBlockID(), Material.water)).setHardness(100F).setLightOpacity(255).setBlockName("Liquid Mercury Stationary");
		
	//Miscellaneous Blocks
	public static final Block oreSalt = (new BlockSaltOre(getNextAvailableBlockID(), 1)).setHardness(2).setResistance(2).setBlockName("Salt Ore");
	
	//Machine Blocks
	public static final Block refinerActive = (new BlockRefiner(getNextAvailableBlockID(), true)).setHardness(100).setBlockName("refinerActive");
	public static final Block refinerInactive = (new BlockRefiner(getNextAvailableBlockID(), false)).setHardness(100).setBlockName("refinerInactive");
	
	public static final Block catalyzerActive = (new BlockCatalyzer(getNextAvailableBlockID(), true)).setHardness(100).setBlockName("catalyzerActive");
	public static final Block catalyzerInactive = (new BlockCatalyzer(getNextAvailableBlockID(), false)).setHardness(100).setBlockName("catalyzerInactive");
	
	public static final Block electricIncudbatorActive = (new BlockElectricIncubator(getNextAvailableBlockID(), true));
	public static final Block electricIncudbatorInactive = (new BlockElectricIncubator(getNextAvailableBlockID(), false));
	
	public static final Block electricIncubatorActive = (new BlockElectricIncubator(getNextAvailableBlockID(), true)).setHardness(100).setBlockName("electricIncubatorActive");
	public static final Block electricIncubatorInactive = (new BlockElectricIncubator(getNextAvailableBlockID(), false)).setHardness(100).setBlockName("electricIncubator");
	
	public static final Block incubatorActive = (new BlockIncubator(getNextAvailableBlockID(), true)).setHardness(100).setBlockName("incubatorActive");
	public static final Block incubatorInactive = (new BlockIncubator(getNextAvailableBlockID(), false));
/*==================================================================Items Start==================================================================*/
	//Tools
	//Armors
	
	//Miscellaneous
	public static Item salt = new ItemSalt(5000).setIconIndex(0).setItemName("Ground Salt");
	public static Item saltedBeef = new ItemSaltedFood(5001, 4, 0.9F, false).setIconIndex(1).setItemName("Salted Beef");
	public static Item saltedPork = new ItemSaltedFood(5002, 4, 0.9F, false).setIconIndex(2).setItemName("Salted Pork");
	public static Item saltedChicken = new ItemSaltedFood(5003, 3, 0.8F, false).setIconIndex(3).setItemName("Salted Chicken");
	
	public static Item refinedSugar = new ItemSugar(5004).setIconIndex(4).setItemName("Sucrose");
	public static Item lactoseSugar = new ItemSugar(5005).setIconIndex(5).setItemName("Lactose");
	public static Item glucose = new ItemSugar(5006).setIconIndex(6).setItemName("Glucose");
	public static Item fructose = new ItemSugar(5007).setIconIndex(7).setItemName("Fructose");
	public static Item galactose = new ItemSugar(5008).setIconIndex(8).setItemName("Galactose");
	
	public static Item rootBeerSoda = new ItemSoda(5009, 2, 0.5F, false).setIconIndex(9).setItemName("Root Beer");
	public static Item grapeSoda = new ItemSoda(5010, 2, 0.5F, false).setIconIndex(10).setItemName("Some Flavor");
	public static Item cherrySoda = new ItemSoda(5011, 2, 0.5F, false).setIconIndex(11).setItemName("Some Flavor");
	
	public static Item lipid = new ItemLipid(5012).setIconIndex(12).setItemName("Lipid");
	public static Item fattyAcid = new ItemLipidComponents(5013).setIconIndex(13).setItemName("Fatty Acid");
	public static Item gylcerol = new ItemLipidComponents(5014).setIconIndex(14).setItemName("Glycerol");
	
	public static Item adenine = new ItemDeoxyriboNucleicAcid(5015).setIconIndex(15).setItemName("AdenineNucleotide");
	public static Item cytosine = new ItemDeoxyriboNucleicAcid(5016).setIconIndex(16).setItemName("CytosineNucleotide");
	public static Item guanine = new ItemDeoxyriboNucleicAcid(5017).setIconIndex(17).setItemName("GuanineNucleotide");
	public static Item thymine = new ItemDeoxyriboNucleicAcid(5018).setIconIndex(18).setItemName("ThymineNucleotide");
	
	public static Item adenineRNA = new ItemRiboNucleicAcid(5019).setIconIndex(19).setItemName("AdenineRNANucleotide");
	public static Item cytosineRNA = new ItemRiboNucleicAcid(5020).setIconIndex(19).setItemName("AdenineRNANucleotide");
	public static Item guanineRNA = new ItemRiboNucleicAcid(5021).setIconIndex(19).setItemName("AdenineRNANucleotide");
	public static Item uracilRNA = new ItemRiboNucleicAcid(5022).setIconIndex(19).setItemName("AdenineRNANucleotide");
	
	//Energy Molecules
	public static Item adenosineDiphosphate = new ItemADP(5023).setIconIndex(19).setItemName("adenosineDiphosphate");
	public static Item adenosineTriPhosphate = new ItemATP(5024).setIconIndex(20).setItemName("adenosineTriphosphate");
	
	public static Item NAD = new ItemNAD(5025).setIconIndex(19).setItemName("NAD+");
	public static Item NADH = new ItemNADH(5026).setIconIndex(19).setItemName("NADH");
	
	public static Item NADP = new ItemNADP(5027).setIconIndex(19).setItemName("NADP");
	public static Item NADPH = new ItemNADPH(5028).setIconIndex(19).setItemName("NADP");
	
	public static Item FAD = new ItemFAD(5029).setIconIndex(19).setItemName("FAD+");
	public static Item FADH = new ItemFADH(5030).setIconIndex(19).setItemName("FADH");
	
	//Item Equipment
	public static Item gasJar = new ItemGasJar(5031, 0).setIconIndex(19).setItemName("gasJar");
	public static Item gasJarHydrogen = new ItemGasJar(5032, Hydrogen.blockID).setIconIndex(19).setItemName("gasJarHelium");
	public static Item gasJarHelium = new ItemGasJar(5033, Helium.blockID).setIconIndex(19).setItemName("gasJarHelium");
	public static Item gasJarNitrogen = new ItemGasJar(5034, Nitrogen.blockID).setIconIndex(19).setItemName("gasJarNitrogen");
	public static Item gasJarOxygen = new ItemGasJar(5035, Oxygen.blockID).setIconIndex(19).setItemName("gasJarOxygen");
	public static Item gasJarFluorine = new ItemGasJar(5036, Fluorine.blockID).setIconIndex(19).setItemName("gasJarFluorine");
	public static Item gasJarNeon = new ItemGasJar(5037, Neon.blockID).setIconIndex(19).setItemName("gasJarNeon");
	public static Item gasJarChlorine = new ItemGasJar(5038, Chlorine.blockID).setIconIndex(19).setItemName("gasJarChlorine");
	public static Item gasJarArgon = new ItemGasJar(5039, Argon.blockID).setIconIndex(19).setItemName("gasJarArgon");
	public static Item gasJarKrypton = new ItemGasJar(5040, Krypton.blockID).setIconIndex(19).setItemName("gasJarKrypton");
	public static Item gasJarXenon = new ItemGasJar(5041, Xenon.blockID).setIconIndex(19).setItemName("gasJarXenon");
	public static Item gasJarRadon = new ItemGasJar(5042, Radon.blockID).setIconIndex(19).setItemName("gasJarRadon");
/*===================================================================Items End===================================================================*/
/*==================================================================Achievements Start==================================================================*/
		
		//First Row
	public static Achievement getHydrogen = (new Achievement(48, "getHydrogen", -2, 4, Hydrogen, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getHelium = (new Achievement(49, "getHelium", 23, 4, Helium, getHydrogen)).setIndependent().registerAchievement();
	
		//Second Row
	public static Achievement getLithium = (new Achievement(50, "getLithium", 6, 5, lithiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBeryl = (new Achievement(51, "getBeryllium", 7, 5, berylOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBoron = (new Achievement(52, "getBoron", 18, 5, boronOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getGraphite = (new Achievement(53, "getGraphite", 19, 5, graphiteOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNitrogen = (new Achievement(54, "getNitrogen", 20, 5, Nitrogen, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getOxygen = (new Achievement(55, "getOxygen", 21, 5, Oxygen, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getFluorine = (new Achievement(56, "getFluorine", 22, 5, Fluorine, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNeon = (new Achievement(57, "getNeon", 23, 5, Neon, (Achievement)null)).setIndependent().registerAchievement();
	
		//Third Row
	public static Achievement getSodium = (new Achievement(58, "getSodium", 6, 6, sodiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getMagnesium = (new Achievement(59, "getMagnesium", 7, 6, magnesiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getAluminium = (new Achievement(60, "getAluminium", 18, 6, aluminiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSilicon = (new Achievement(61, "getSilicon", 19, 6, siliconOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPhosphorus = (new Achievement(62, "getPhosphorus", 20, 6, phosphorusOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSulfur = (new Achievement(63, "getSulfur", 21, 6, sulfurOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getChlorine = (new Achievement(64, "getChlorine", 22, 6, Chlorine, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getArgon = (new Achievement(65, "getArgon", 23, 6, Argon, (Achievement)null)).setIndependent().registerAchievement();
	
		//Fourth Row
	public static Achievement getPotassium = (new Achievement(66, "getPotassium", 6, 7, potassiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCalcium = (new Achievement(67, "getCalcium", 7, 7, calciumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getScandium = (new Achievement(68, "getScandium", 8, 7, scandiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTitanium = (new Achievement(69, "getTitanium", 9, 7, titaniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getVanadium = (new Achievement(70, "getVanadium", 10, 7, vanadiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getChromium = (new Achievement(71, "getChromium", 11, 7, chromiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getManganese = (new Achievement(72, "getManganese", 12, 7, manganeseOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getIron = (new Achievement(200, "getIron", 13, 7, Block.oreIron, (Achievement)null)).setIndependent().registerAchievement();;
	public static Achievement getCobalt = (new Achievement(73, "getCobalt", 14, 7, cobaltOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNickel = (new Achievement(74, "getNickel", 15, 7, nickelOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCopper = (new Achievement(75, "getCopper", 16, 7, copperOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getZinc = (new Achievement(76, "getZinc", 17, 7, zincOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getGallium = (new Achievement(77, "getGallium", 18, 7, galliumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getGermanium = (new Achievement(78, "getGermanium", 19, 7, germaniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getArsenic = (new Achievement(79, "getArsenic", 20, 7, arsenicOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSelenium = (new Achievement(80, "getSelenium", 21, 7, seleniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBromine = (new Achievement(81, "getBromine", 22, 7, liquidBromineFlowing, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getKrypton = (new Achievement(82, "gettKrypton", 23, 7, Krypton, (Achievement)null)).setIndependent().registerAchievement();
	
		//Fifth Row
	public static Achievement getRubidium = (new Achievement(83, "getRubidium", 6, 8, rubidiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getStrontium = (new Achievement(84, "getStrontium", 7, 8, strontiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getYttrium = (new Achievement(85, "getYttrium", 8, 8, yttriumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getZirconium = (new Achievement(86, "getZirconium", 9, 8, zirconiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNiobium = (new Achievement(87, "getNiobium", 10, 8, niobiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getMolybdenum = (new Achievement(88, "getMolybdenum", 11, 8, molybdenumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTechnetium = (new Achievement(89, "getTechnetium", 12, 8, technetiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRuthenium = (new Achievement(90, "getRuthenium", 13, 8, rutheniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRhodium = (new Achievement(91, "getRhodium", 14, 8, rhodiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPalladium = (new Achievement(92, "getPalladium", 15, 8, palladiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSilver = (new Achievement(93, "getSilver", 16, 8, silverOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCadmium = (new Achievement(94, "getCadmium", 17, 8, cadmiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getIndium = (new Achievement(95, "getIndium", 18, 8, indiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTin = (new Achievement(96, "getTin", 19, 8, tinOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getAntimony = (new Achievement(97, "getAntimony", 20, 8, antimonyOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTellurium = (new Achievement(98, "getTellurium", 21, 8, telluriumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getIodine = (new Achievement(99, "getIodine", 22, 8, iodineOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getXenon = (new Achievement(201, "getXenon", 23, 8, Xenon, (Achievement)null)).setIndependent().registerAchievement();
	
		//Sixth Row
	public static Achievement getCaesium = (new Achievement(100, "getCaesium", 6, 9, caesiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBarium = (new Achievement(101, "getBarium", 7, 9, bariumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getHafnium = (new Achievement(117, "getHafnium", 9, 9, hafniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTantalum = (new Achievement(118, "getTantalum", 10, 9, tantalumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTungsten = (new Achievement(119, "getTungsten", 11, 9, tungstenOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRhenium = (new Achievement(120, "getRhenium", 12, 9, rheniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getOsmium = (new Achievement(121, "getOsmium", 13, 9, osmiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getIridium = (new Achievement(122, "getIridium", 14, 9, iridiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPlatinum = (new Achievement(123, "getPlatinum", 15, 9, platinumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getGold = (new Achievement(211, "getGold", 16, 9, Block.oreGold, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getMercury = (new Achievement(124, "getMercury", 17, 9, liquidMercuryFlowing, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getThallium = (new Achievement(125, "getThallium", 18, 9, thalliumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getLead = (new Achievement(126, "getLead", 19, 9, leadOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBismuth = (new Achievement(127, "getBismuth", 20, 9, bismuthOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPolonium = (new Achievement(128, "getPolonium", 21, 9, poloniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getAstatine = (new Achievement(129, "getAstatine", 22, 9, astatineOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRadon = (new Achievement(210, "getRadon", 23, 9, Radon, (Achievement)null)).setIndependent().registerAchievement();
	
		//Seventh Row
	public static Achievement getFrancium = (new Achievement(130, "getFrancium", 6, 10, franciumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRadium = (new Achievement(131, "getRadium", 7, 10, radiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRutherfordium = (new Achievement(147, "getRutherfordium", 9, 10, rutherfordiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getDubnium = (new Achievement(148, "getDubnium", 10, 10, dubniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSeaborgium = (new Achievement(149, "getSeaborgium", 11, 10, seaborgiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBohrium = (new Achievement(150, "getBohrium", 12, 10, bohriumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getHassium = (new Achievement(151, "getHassium", 13, 10, hassiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getMeitnerium = (new Achievement(152, "getMeitnerium", 14, 10, meitneriumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getDarmstadtium = (new Achievement(153, "getDarmstadtium", 15, 10, darmstadtiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRoentgenium = (new Achievement(154, "getRoentgenium", 16, 10, roentgeniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCopernicium = (new Achievement(155, "getCopernicium", 17, 10, coperniciumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getUnuntrium = (new Achievement(156, "getUnuntrium", 18, 10, ununtriumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();
	public static Achievement getUnunquadium = (new Achievement(157, "getQuadium", 19, 10, ununquadiumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();
	public static Achievement getUnunpentium = (new Achievement(158, "getUnunpentium", 20, 10, ununpentiumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();
	public static Achievement getUnunhexium = (new Achievement(159, "getUnunhexium", 21, 10, ununhexiumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();
	public static Achievement getUnunseptium = (new Achievement(160, "getUnunseptium", 22, 10, ununseptiumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();
	public static Achievement getUnunoctium = (new Achievement(161, "getUnunoctium", 23, 10, ununoctiumOre, (Achievement)null)).setSpecial().setIndependent().registerAchievement();

		//Lanthanoids
	public static Achievement getLanthanum = (new Achievement(102, "getLanthanum", 9, 12, lanthanumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCerium = (new Achievement(103, "getCerium", 10, 12, ceriumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPraseodymium = (new Achievement(104, "getPraseodymium", 11, 12, praseodymiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNeodymium = (new Achievement(105, "getNeodymium", 12, 12, neodymiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPromethium = (new Achievement(106, "getPromethium", 13, 12, promethiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getSamarium = (new Achievement(107, "getSamarium", 14, 12, samariumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getEuropium = (new Achievement(108, "getEuropium", 15, 12, europiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getGadolinium = (new Achievement(109, "getGadolinium", 16, 12, gadoliniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getTerbium = (new Achievement(110, "getTerbium", 17, 12, terbiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getDysprosium = (new Achievement(111, "getDysprosium", 18, 12, dysprosiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getHolmium = (new Achievement(112, "getHolmium", 19, 12, holmiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getErbium = (new Achievement(113, "getErbium", 20, 12, erbiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getThulium = (new Achievement(114, "getThulium", 21, 12, thuliumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getYtterbium = (new Achievement(115, "getYtterbium", 22, 12, ytterbiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getLutetium = (new Achievement(116, "getLutetium", 23, 12, lutetiumOre, (Achievement)null)).setIndependent().registerAchievement();
	
		//Actinoids
	public static Achievement getActinium = (new Achievement(132, "getActinium", 9, 13, actiniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getThorium = (new Achievement(133, "getThorium", 10, 13, thoriumOre, (Achievement)null)).setIndependent().registerAchievement();	
	public static Achievement getProtactinium = (new Achievement(134, "getProtactinium", 11, 13, protactiniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getUranium = (new Achievement(135, "getUranium", 12, 13, uraniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNeptunium = (new Achievement(136, "getNeptunium", 13, 13, neptuniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getPlutonium = (new Achievement(137, "getPlutonium", 14, 13, plutoniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getAmericium = (new Achievement(138, "getAmericium", 15, 13, americiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCurium = (new Achievement(139, "getCurium", 16, 13, curiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getBerkelium = (new Achievement(140, "getBerkelium", 17, 13, berkeliumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getCalifornium = (new Achievement(141, "getCalifornium", 18, 13, californiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getEinsteinium = (new Achievement(142, "getEinsteinium", 19, 13, einsteiniumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getFermium = (new Achievement(143, "getFermium", 20, 13, fermiumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getMendelevium = (new Achievement(144, "getMendelevium", 21, 13, mendeleviumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getNobelium = (new Achievement(145, "getNobelium", 22, 13, nobeliumOre, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getLawrencium = (new Achievement(146, "getLawrencium", 23, 13, lawrenciumOre, (Achievement)null)).setIndependent().registerAchievement();
	
	//SC Achieves
	public static Achievement getRefinedSalt = (new Achievement(147, "getSalt", 0, 0, salt, (Achievement)null)).setIndependent().registerAchievement();
	public static Achievement getRefinedSugar = (new Achievement(148, "getSugar", 0, -2, refinedSugar, (Achievement)null)).setIndependent().registerAchievement();
	
	
	public static AchievementPage ScienceCraftPage = new AchievementPage("ScienceCraft", getRefinedSalt, getRefinedSugar); 
	
	public static AchievementPage SCPeriodicTablePage = new AchievementPage("Elements Mod",
			getHydrogen, getHelium, getLithium, getBeryl, getBoron, getGraphite, 
			getNitrogen, getOxygen, getFluorine, getNeon, getSodium, getMagnesium,
			getAluminium, getSilicon, getPhosphorus, getSulfur, getChlorine, 
			getArgon, getPotassium, getCalcium, getScandium, getTitanium, getVanadium,
			getChromium, getManganese, getIron, getCobalt, getNickel, getCopper, getZinc,
			getGallium, getGermanium, getArsenic, getSelenium, getBromine, getKrypton,
			getRubidium, getStrontium, getYttrium, getZirconium, getNiobium, getMolybdenum,
			getTechnetium, getRuthenium, getRhodium, getPalladium, getSilver, getCadmium, 
			getIndium, getTin, getAntimony, getTellurium, getIodine, getXenon, getCaesium,
			getBarium, getBarium, getHafnium, getTantalum, getTungsten, getRhenium, getOsmium,
			getIridium, getPlatinum, getGold, getMercury, getThallium, getLead, getBismuth,
			getPolonium, getAstatine, getRadon, getFrancium, getRadium, getRutherfordium,
			getDubnium, getSeaborgium, getBohrium, getHassium, getMeitnerium, getDarmstadtium,
			getRoentgenium, getCopernicium, getUnuntrium, getUnunquadium, getUnunpentium, 
			getUnunhexium, getUnunseptium, getUnunoctium, getLanthanum, getCerium, 
			getPraseodymium, getNeodymium, getPromethium, getSamarium, getEuropium, getGadolinium,
			getTerbium, getDysprosium, getHolmium, getErbium, getThulium, getYtterbium,
			getLutetium, getActinium, getThorium, getProtactinium, getUranium, getNeptunium,
			getPlutonium, getAmericium, getCurium, getBerkelium, getCalifornium, getEinsteinium, 
			getFermium, getMendelevium, getNobelium, getLawrencium);
	
/*===================================================================Achievements End===================================================================*/
	
	public void registerBlocks()
	{
		try{
			//Gas Blocks
			GameRegistry.registerBlock(Hydrogen);
			GameRegistry.registerBlock(Helium);
			GameRegistry.registerBlock(Nitrogen);
			GameRegistry.registerBlock(Oxygen);
			GameRegistry.registerBlock(Fluorine);
			GameRegistry.registerBlock(Neon);
			GameRegistry.registerBlock(Chlorine);
			GameRegistry.registerBlock(Argon);
			GameRegistry.registerBlock(Krypton);
			GameRegistry.registerBlock(Xenon);
			GameRegistry.registerBlock(Radon);
			
			//Solid Blocks
			GameRegistry.registerBlock(lithiumOre);
			GameRegistry.registerBlock(berylOre);
			GameRegistry.registerBlock(boronOre);
			GameRegistry.registerBlock(graphiteOre);
			GameRegistry.registerBlock(sodiumOre);
			GameRegistry.registerBlock(magnesiumOre);
			GameRegistry.registerBlock(aluminiumOre);
			GameRegistry.registerBlock(siliconOre);
			GameRegistry.registerBlock(phosphorusOre);
			GameRegistry.registerBlock(sulfurOre);
			GameRegistry.registerBlock(potassiumOre);
			GameRegistry.registerBlock(calciumOre);
			GameRegistry.registerBlock(scandiumOre);
			GameRegistry.registerBlock(titaniumOre);
			GameRegistry.registerBlock(vanadiumOre);
			GameRegistry.registerBlock(chromiumOre);
			GameRegistry.registerBlock(manganeseOre);
			GameRegistry.registerBlock(cobaltOre);
			GameRegistry.registerBlock(nickelOre);
			GameRegistry.registerBlock(copperOre);
			GameRegistry.registerBlock(zincOre);
			GameRegistry.registerBlock(galliumOre);
			GameRegistry.registerBlock(germaniumOre);
			GameRegistry.registerBlock(arsenicOre);
			GameRegistry.registerBlock(seleniumOre);
			GameRegistry.registerBlock(rubidiumOre);
			GameRegistry.registerBlock(strontiumOre);
			GameRegistry.registerBlock(yttriumOre);
			GameRegistry.registerBlock(zirconiumOre);
			GameRegistry.registerBlock(niobiumOre);
			GameRegistry.registerBlock(molybdenumOre);
			GameRegistry.registerBlock(technetiumOre);
			GameRegistry.registerBlock(rutheniumOre);
			GameRegistry.registerBlock(rhodiumOre);
			GameRegistry.registerBlock(palladiumOre);
			GameRegistry.registerBlock(silverOre);
			GameRegistry.registerBlock(cadmiumOre);
			GameRegistry.registerBlock(indiumOre);
			GameRegistry.registerBlock(tinOre);
			GameRegistry.registerBlock(antimonyOre);
			GameRegistry.registerBlock(telluriumOre);
			GameRegistry.registerBlock(iodineOre);
			GameRegistry.registerBlock(caesiumOre);
			GameRegistry.registerBlock(bariumOre);
			GameRegistry.registerBlock(lanthanumOre);
			GameRegistry.registerBlock(ceriumOre);
			GameRegistry.registerBlock(praseodymiumOre);
			GameRegistry.registerBlock(neodymiumOre);
			GameRegistry.registerBlock(promethiumOre);
			GameRegistry.registerBlock(samariumOre);
			GameRegistry.registerBlock(europiumOre);
			GameRegistry.registerBlock(gadoliniumOre);
			GameRegistry.registerBlock(terbiumOre);
			GameRegistry.registerBlock(dysprosiumOre);
			GameRegistry.registerBlock(holmiumOre);
			GameRegistry.registerBlock(erbiumOre);
			GameRegistry.registerBlock(thuliumOre);
			GameRegistry.registerBlock(ytterbiumOre);
			GameRegistry.registerBlock(lutetiumOre);
			GameRegistry.registerBlock(hafniumOre);
			GameRegistry.registerBlock(tantalumOre);
			GameRegistry.registerBlock(tungstenOre);
			GameRegistry.registerBlock(rheniumOre);
			GameRegistry.registerBlock(osmiumOre);
			GameRegistry.registerBlock(iridiumOre);
			GameRegistry.registerBlock(platinumOre);
			GameRegistry.registerBlock(thalliumOre);
			GameRegistry.registerBlock(leadOre);
			GameRegistry.registerBlock(bismuthOre);
			GameRegistry.registerBlock(poloniumOre);
			GameRegistry.registerBlock(astatineOre);
			GameRegistry.registerBlock(franciumOre);
			GameRegistry.registerBlock(radiumOre);
			GameRegistry.registerBlock(actiniumOre);
			GameRegistry.registerBlock(thoriumOre);
			GameRegistry.registerBlock(protactiniumOre);
			GameRegistry.registerBlock(uraniumOre);
			GameRegistry.registerBlock(neptuniumOre);
			GameRegistry.registerBlock(plutoniumOre);
			GameRegistry.registerBlock(americiumOre);
			GameRegistry.registerBlock(curiumOre);
			GameRegistry.registerBlock(berkeliumOre);
			GameRegistry.registerBlock(californiumOre);
			GameRegistry.registerBlock(einsteiniumOre);
			GameRegistry.registerBlock(fermiumOre);
			GameRegistry.registerBlock(mendeleviumOre);
			GameRegistry.registerBlock(nobeliumOre);
			GameRegistry.registerBlock(lawrenciumOre);
			GameRegistry.registerBlock(rutherfordiumOre);
			GameRegistry.registerBlock(dubniumOre);
			GameRegistry.registerBlock(seaborgiumOre);
			GameRegistry.registerBlock(bohriumOre);
			GameRegistry.registerBlock(hassiumOre);
			GameRegistry.registerBlock(meitneriumOre);
			GameRegistry.registerBlock(darmstadtiumOre);
			GameRegistry.registerBlock(roentgeniumOre);
			GameRegistry.registerBlock(coperniciumOre);
			GameRegistry.registerBlock(ununtriumOre);
			GameRegistry.registerBlock(ununquadiumOre);
			GameRegistry.registerBlock(ununpentiumOre);
			GameRegistry.registerBlock(ununhexiumOre);
			GameRegistry.registerBlock(ununseptiumOre);
			GameRegistry.registerBlock(ununoctiumOre);
			
			
			//Liquid Blocks
			GameRegistry.registerBlock(liquidBromineStationary);
			GameRegistry.registerBlock(liquidBromineFlowing);
			GameRegistry.registerBlock(liquidMercuryStationary);
			GameRegistry.registerBlock(liquidMercuryFlowing);
			
			//Salt
			GameRegistry.registerBlock(oreSalt);
			
			//Machine Blocks
			GameRegistry.registerBlock(refinerActive);
			GameRegistry.registerBlock(refinerInactive);
			
			//GameRegistry.registerBlock(catalyzerActive);
			GameRegistry.registerBlock(catalyzerInactive);
			}catch(NullPointerException e)
			{
				e.printStackTrace();
			}
	}

	public void registerNames() 
	{
		try{
			//Gas Blocks
			LanguageRegistry.addName(Hydrogen, "Hydrogen Gas");
			LanguageRegistry.addName(Helium, "Helium Gas");
			LanguageRegistry.addName(Nitrogen, "Nitrogen Gas");
			LanguageRegistry.addName(Oxygen, "Oxygen Gas");
			LanguageRegistry.addName(Fluorine, "Fluorine Gas");
			LanguageRegistry.addName(Neon, "Neon Gas");
			LanguageRegistry.addName(Chlorine, "Chlorine Gas");
			LanguageRegistry.addName(Argon, "Argon Gas");	
			LanguageRegistry.addName(Krypton, "Krypton Gas");
			LanguageRegistry.addName(Xenon, "Xenon Gas");
			LanguageRegistry.addName(Radon, "Radon Gas");
			
			//Solid Blocks
			LanguageRegistry.addName(lithiumOre, "Lithium Ore");
			LanguageRegistry.addName(berylOre, "Beryl Ore");
			LanguageRegistry.addName(boronOre, "Boron Ore");
			LanguageRegistry.addName(graphiteOre, "Carbon Ore");
			LanguageRegistry.addName(sodiumOre, "Sodium Ore");
			LanguageRegistry.addName(magnesiumOre, "Magnesium Ore");
			LanguageRegistry.addName(aluminiumOre, "Aluminium Ore");
			LanguageRegistry.addName(siliconOre, "Silicon Ore");
			LanguageRegistry.addName(phosphorusOre, "Phosphorus Ore");
			LanguageRegistry.addName(sulfurOre, "Sulfur Ore");
			LanguageRegistry.addName(potassiumOre, "Potassium Ore");
			LanguageRegistry.addName(calciumOre, "Calcium Ore");
			LanguageRegistry.addName(scandiumOre, "Scadnium Ore");
			LanguageRegistry.addName(titaniumOre, "Titanium Ore");
			LanguageRegistry.addName(vanadiumOre, "Vanadium Ore");
			LanguageRegistry.addName(chromiumOre, "Chromium Ore");
			LanguageRegistry.addName(manganeseOre, "Manganese Ore");
			LanguageRegistry.addName(cobaltOre, "Cobalt Ore");
			LanguageRegistry.addName(nickelOre, "Nickel Ore");
			LanguageRegistry.addName(copperOre, "Copper Ore");
			LanguageRegistry.addName(zincOre, "Zinc Ore");
			LanguageRegistry.addName(galliumOre, "Gallium Ore");
			LanguageRegistry.addName(germaniumOre, "Germanium Ore");
			LanguageRegistry.addName(arsenicOre, "Arsenic Ore");
			LanguageRegistry.addName(seleniumOre, "Selenium Ore");
			LanguageRegistry.addName(rubidiumOre, "Rubidium Ore");
			LanguageRegistry.addName(strontiumOre, "Strontium Ore");
			LanguageRegistry.addName(yttriumOre, "Yttrium Ore");
			LanguageRegistry.addName(zirconiumOre, "Zirconium Ore");
			LanguageRegistry.addName(niobiumOre, "Niobium Ore");
			LanguageRegistry.addName(molybdenumOre, "Molybdenum Ore");
			LanguageRegistry.addName(technetiumOre, "Technetium Ore");
			LanguageRegistry.addName(rutheniumOre, "Ruthenium Ore");
			LanguageRegistry.addName(rhodiumOre, "Rhodium Ore");
			LanguageRegistry.addName(palladiumOre, "Palladium Ore");
			LanguageRegistry.addName(silverOre, "Silver Ore");
			LanguageRegistry.addName(cadmiumOre, "Cadmium Ore");
			LanguageRegistry.addName(indiumOre, "Indium Ore");
			LanguageRegistry.addName(tinOre, "Tin Ore");
			LanguageRegistry.addName(antimonyOre, "Antimony Ore");
			LanguageRegistry.addName(telluriumOre, "Tellurium Ore");
			LanguageRegistry.addName(iodineOre, "Iodine Ore");
			LanguageRegistry.addName(caesiumOre, "Caesium Ore");
			LanguageRegistry.addName(bariumOre, "Barium Ore");
			LanguageRegistry.addName(lanthanumOre, "Lanthanum Ore");
			LanguageRegistry.addName(ceriumOre, "Cerium Ore");
			LanguageRegistry.addName(praseodymiumOre, "Prasedymium");
			LanguageRegistry.addName(neodymiumOre, "Neodymium Ore");
			LanguageRegistry.addName(promethiumOre, "Promethium Ore");
			LanguageRegistry.addName(samariumOre, "Samarium Ore");
			LanguageRegistry.addName(europiumOre, "Europium Ore");
			LanguageRegistry.addName(gadoliniumOre, "Gadolinium Ore");
			LanguageRegistry.addName(terbiumOre, "Terbium Ore");
			LanguageRegistry.addName(dysprosiumOre, "Dysprosium Ore");
			LanguageRegistry.addName(holmiumOre, "Holmium Ore");
			LanguageRegistry.addName(erbiumOre, "Erbium Ore");
			LanguageRegistry.addName(thuliumOre, "Thulium Ore");
			LanguageRegistry.addName(ytterbiumOre, "Ytterbium Ore");
			LanguageRegistry.addName(lutetiumOre, "Lutetium Ore");
			LanguageRegistry.addName(hafniumOre, "Hafnium Ore");
			LanguageRegistry.addName(tantalumOre, "Tantalum Ore");
			LanguageRegistry.addName(tungstenOre, "Tungsten Ore");
			LanguageRegistry.addName(rheniumOre, "Rhenium Ore");
			LanguageRegistry.addName(osmiumOre, "Osmium Ore");
			LanguageRegistry.addName(iridiumOre, "Iridium Ore");
			LanguageRegistry.addName(platinumOre, "Platinum Ore");
			LanguageRegistry.addName(thalliumOre, "Thallium Ore");
			LanguageRegistry.addName(leadOre, "Lead Ore");
			LanguageRegistry.addName(bismuthOre, "Bismuth Ore");
			LanguageRegistry.addName(poloniumOre, "Polonium Ore");
			LanguageRegistry.addName(astatineOre, "Astatine Ore");
			LanguageRegistry.addName(franciumOre, "Francium Ore");
			LanguageRegistry.addName(radiumOre, "Radium Ore");
			LanguageRegistry.addName(actiniumOre, "Actinium Ore");
			LanguageRegistry.addName(thoriumOre, "Thorium Ore");
			LanguageRegistry.addName(protactiniumOre, "Protactinium Ore");
			LanguageRegistry.addName(uraniumOre, "Uranium Ore");
			LanguageRegistry.addName(neptuniumOre, "Neptunium Ore");
			LanguageRegistry.addName(plutoniumOre, "Plutonium Ore");
			LanguageRegistry.addName(americiumOre, "Americium Ore");
			LanguageRegistry.addName(curiumOre, "Curium Ore");
			LanguageRegistry.addName(berkeliumOre, "Berkelium Ore");
			LanguageRegistry.addName(californiumOre, "Californium Ore");
			LanguageRegistry.addName(einsteiniumOre, "Einsteinium Ore");
			LanguageRegistry.addName(fermiumOre, "Fermium Ore");
			LanguageRegistry.addName(mendeleviumOre, "Mendelevium Ore");
			LanguageRegistry.addName(nobeliumOre, "Nobelium Ore");
			LanguageRegistry.addName(lawrenciumOre, "Lawrencium Ore");
			LanguageRegistry.addName(rutherfordiumOre, "Rutherfordium");
			LanguageRegistry.addName(dubniumOre, "Dubnium Ore");
			LanguageRegistry.addName(seaborgiumOre, "Seaborgium Ore");
			LanguageRegistry.addName(bohriumOre, "Bohrium");
			LanguageRegistry.addName(hassiumOre, "Hassium Ore");
			LanguageRegistry.addName(meitneriumOre, "Meitnerium Ore");
			LanguageRegistry.addName(darmstadtiumOre, "Darmstadtium Ore");
			LanguageRegistry.addName(roentgeniumOre, "Roentgenium Ore");
			LanguageRegistry.addName(coperniciumOre, "Copernicium Ore");
			LanguageRegistry.addName(ununtriumOre, "Ununtrium Ore");
			LanguageRegistry.addName(ununquadiumOre, "Ununquadium Ore");
			LanguageRegistry.addName(ununpentiumOre, "Ununpentium Ore");
			LanguageRegistry.addName(ununhexiumOre, "Ununhexium Ore");
			LanguageRegistry.addName(ununseptiumOre, "Ununseptium Ore");
			LanguageRegistry.addName(ununoctiumOre, "Ununoctium Ore");
			
			//Liquid Blocks
			LanguageRegistry.addName(liquidBromineFlowing, "Liquid Bromine");
			LanguageRegistry.addName(liquidBromineStationary, "Liquid Bromine Stationary");
			LanguageRegistry.addName(liquidMercuryFlowing, "Liquid Mercury");
			LanguageRegistry.addName(liquidMercuryStationary, "Liquid Mercury Stationary");
			
			//Miscellaneous Blocks
			LanguageRegistry.addName(oreSalt, "Salt Ore");
			
			//Miscellaneous Items
			LanguageRegistry.addName(salt, "Salt");
			LanguageRegistry.addName(saltedBeef, "Salted Beef");
			LanguageRegistry.addName(saltedChicken, "Salted Chicken");
			LanguageRegistry.addName(saltedPork, "Salted Pork");
			
			//Machine Blocks
			LanguageRegistry.addName(refinerActive, "Active Refiner");
			LanguageRegistry.addName(refinerInactive, "Refiner");
			
			LanguageRegistry.addName(catalyzerActive, "Active Catalyzer");
			LanguageRegistry.addName(catalyzerInactive, "Catalyzer");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void registerRecipes()
	{
		try{
			GameRegistry.addRecipe(new ItemStack(liquidBromineFlowing), new Object[]{
				"F", Character.valueOf('F'), Block.dirt
			});
			
			GameRegistry.addRecipe(new ItemStack(Item.bucketWater), new Object[]{
				"FF", Character.valueOf('F'), Block.dirt
			});
			
			GameRegistry.addRecipe(new ItemStack(Radon), new Object[] {
				"FFF", Character.valueOf('F'), Block.dirt
			});
				
			GameRegistry.addShapelessRecipe(new ItemStack(saltedBeef), new Object[] 
			{
				Item.beefCooked, salt, salt, salt
			});
			GameRegistry.addShapelessRecipe(new ItemStack(saltedPork), new Object[] 
			{
				Item.porkCooked, salt, salt, salt
			});
			GameRegistry.addShapelessRecipe(new ItemStack(saltedChicken), new Object[] 
			{
				Item.chickenCooked, salt, salt, salt
			});
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void registerBlockHarvestLevels()
	{
		MinecraftForge.setBlockHarvestLevel(oreSalt, "pickaxe", 1);
	}
	
	public void registerHandlers()
	{
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
		
		NetworkRegistry.instance().registerGuiHandler(ScienceCraft.instance(), new GUIHandler());
		
		MinecraftForge.EVENT_BUS.register(new EventManager());
		
		GameRegistry.registerFuelHandler(new FuelHandler());
		GameRegistry.registerWorldGenerator(new SCWorldGenerator());
		GameRegistry.registerCraftingHandler(new CraftingHandler());
		GameRegistry.registerPickupHandler(new PickupNotifier());
		GameRegistry.registerDispenserHandler(new DispenserHandler());
		GameRegistry.registerPlayerTracker(new PlayerTracker());

		KeyBindingRegistry.registerKeyBinding(new KeyBindings());
	}
	
	public void registerAchievements()
	{
		AchievementPage.registerAchievementPage(ScienceCraftPage);
		AchievementPage.registerAchievementPage(SCPeriodicTablePage);
	}
	
	public static void registerContainerInfo()
	{
		//Refiner
		registerContainerLocalization("refiner", "Refiner");
		TileEntity.addMapping(TileEntityRefiner.class, "Refiner");
		
		registerContainerLocalization("teleporter", "Teleporter");
		TileEntity.addMapping(TileEntityTeleporter.class, "teleporter");
	}
	
	public static void registerAchInfo()
	{
		addAchievementInfoToArray(0, "getHydrogen", "Got Hydrogen!", "You managed to find Hydrogen!");
		addAchievementInfoToArray(1, "getHelium", "Got Helium!", "You managed to find Helium!");
	}
	
	private static void addAchievementInfoToArray(int id, String achievement, String name, String desc)
	{
		SCAchs.put(id, achievement);
		SCAchNames.put(id, name);
		SCAchDescs.put(id, desc);
	}
	
	public static void registerAchievementNames() 
	{
		for(int i = 0; i < SCAchs.size(); i++)
		{
			registerAchievementNameLocalization(SCAchs.get(i), SCAchNames.get(i));
			registerAchievementDescLocalization(SCAchs.get(i), SCAchDescs.get(i));
		}
	}

	private static void registerAchievementNameLocalization(String string, String string2) 
	{
		LanguageRegistry.instance().addStringLocalization("achievement." + string, "en_US",  string2);
	}
	
	private static void registerAchievementDescLocalization(String string, String string2) 
	{
		LanguageRegistry.instance().addStringLocalization("achievement." + string + ".desc", "en_US",  string2);
	}
	
	public static void registerContainerLocalization(String name, String value)
	{
		LanguageRegistry.instance().addStringLocalization("container." + name, "en_US", value);
	}

	//Achievement Triggering (These methods are called inside the Slot classes for the individual Machines)
	public void onIncubated(ItemStack itemstack, EntityPlayer player) 
	{
		
	}
	
	public void onRefined(ItemStack itemstack, EntityPlayer player)
	{
		
	}
	
	public void onCatalyzed(ItemStack itemstack, EntityPlayer player)
	{
		
	}
}
