package net.sciencecraft.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.sciencecraft.src.machineblocks.catalyzer.ContainerCatalyzer;
import net.sciencecraft.src.machineblocks.catalyzer.TileEntityCatalyzer;
import net.sciencecraft.src.machineblocks.refiner.ContainerRefiner;
import net.sciencecraft.src.machineblocks.refiner.TileEntityRefiner;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void registerRenderInformation()
	{
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null)
		{
			switch(ID)
			{
			case 0: 
				return new ContainerRefiner(player.inventory, (TileEntityRefiner)te);
			case 1: 
				return new ContainerCatalyzer(player.inventory, (TileEntityCatalyzer)te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}
}
