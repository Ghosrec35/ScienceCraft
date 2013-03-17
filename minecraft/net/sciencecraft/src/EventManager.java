package net.sciencecraft.src;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.IWorldGenerator;

public class EventManager implements ICraftingHandler, IFuelHandler, IPickupNotifier, IWorldGenerator, IPlayerTracker
{
	@ForgeSubscribe
	public void soundLoader(SoundLoadEvent event)
	{
		//event.manager.soundPoolSounds.addSound("refiner.running", new File(Minecraft.getMinecraftDir(), "/ScienceCraft/Refiner/RefinerRunning.ogg"));
	}

	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		return 0;
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) 
	{
		
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) 
	{
		
	}

	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) 
	{
		
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) 
	{
		
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.dimensionId)
		{
		case -1: generateNether(world, random, chunkX * 16, chunkZ * 16);
		case 0: generateSurface(world, random, chunkX * 16, chunkZ * 16);
		case 1: generateEnd(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	private void generateSurface(World world, Random random, int posX, int posZ) 
	{
		
	}

	private void generateNether(World world, Random random, int posX, int posZ) 
	{
		
	}

	private void generateEnd(World world, Random random, int posX, int posZ) 
	{
		
	}
}
