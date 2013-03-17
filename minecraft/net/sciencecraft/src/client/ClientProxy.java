package net.sciencecraft.src.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.sciencecraft.src.CommonProxy;
import net.sciencecraft.src.ScienceCraft;
import net.sciencecraft.src.machineblocks.catalyzer.GuiCatalyzer;
import net.sciencecraft.src.machineblocks.catalyzer.TileEntityCatalyzer;
import net.sciencecraft.src.machineblocks.refiner.GuiRefiner;
import net.sciencecraft.src.machineblocks.refiner.TileEntityRefiner;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCBlockFile);
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCItemFile);
		MinecraftForgeClient.preloadTexture(ScienceCraft.SCDirectory + ScienceCraft.SCMachinesFile);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null)
		{
			switch(ID)
			{
			case 0: 
				return new GuiRefiner(player.inventory, (TileEntityRefiner)te);
			case 1: 
				return new GuiCatalyzer(player.inventory, (TileEntityCatalyzer)te);
			}
		}
		return null;
	}
}
