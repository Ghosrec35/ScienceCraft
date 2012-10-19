package net.sciencecraft.src;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import universalelectricity.UniversalElectricity;

import net.sciencecraft.src.blocks.*;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.*;
import net.sciencecraft.src.blocks.*;
import net.sciencecraft.src.client.ClientTickHandler;
import net.sciencecraft.src.items.*;
import net.sciencecraft.src.machineblocks.catalyzer.BlockCatalyzer;
import net.sciencecraft.src.machineblocks.incubator.BlockElectricIncubator;
import net.sciencecraft.src.machineblocks.refiner.*;
import net.sciencecraft.src.machineblocks.teleporter.TeleportationManager;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.SidedProxy;


@NetworkMod
(
		clientSideRequired = true,
		serverSideRequired = false,
		channels = "ScienceCraft",
		packetHandler = PacketHandler.class,
		versionBounds = "[4.1]"
)
@Mod
(
		modid = "Ghosrec35_ScienceCraft",
		name = "ScienceCraft",
		version = "1.0.0.0",
		dependencies = "after:UniversalElectricity"
)
public class ScienceCraft 
{
	@SidedProxy
	(
			clientSide = "net.sciencecraft.src.client.ClientProxy",
			serverSide = "net.sciencecraft.src.CommonProxy"
	)
	public static CommonProxy proxy;
	
	//Directories
	public static String SCDirectory = "/ScienceCraft";
	public static String SCBlockDir = "/Blocks";
	public static String SCItemDir = "/Items";
	
	//Texture Files
	public static String SCBlockFile = SCDirectory + SCBlockDir + "/Blocks.png";
	public static String SCItemFile = SCDirectory + SCItemDir + "/Items.png";
	public static String SCMachinesFile = SCDirectory + SCBlockDir + "/MachineBlocks.png";
	
	//Handler instance
	public static SCRegistrationHandler registry = SCRegistrationHandler.instance();
	
	@Instance("ScienceCraft") 
	private static ScienceCraft instance;
	
	public static ScienceCraft instance()
	{
		return instance;
	}
	
	@PreInit
	public void loadPre(FMLPreInitializationEvent event)
	{
		instance = this;
	}
	
	@Init
	public void load(FMLInitializationEvent event)
	{
		registry.registerBlocks();
		registry.registerNames();
		registry.registerBlockHarvestLevels();
		registry.registerRecipes();
		registry.registerBlockHarvestLevels();
		registry.registerAchievements();
		registry.registerContainerInfo();
		registry.registerAchInfo();
		registry.registerAchievementNames();
		registry.registerHandlers();
		
		UniversalElectricity.registerMod(this, "ScienceCraft", "0.6.0");
		
		proxy.registerRenderInformation();
	}

	@PostInit
	public void loadPost(FMLPostInitializationEvent event)
	{
		
	}
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.getServer().setMOTD("Welcome!\nThis server contains the mod ScienceCraft. Be sure to have all parts of ScienceCraft installed to avoid bugs!");
	}
	
    @ServerStarted
    public void serverStarted(FMLServerStartedEvent event)
    {
    	
    }
}
