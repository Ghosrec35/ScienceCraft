package net.sciencecraft.src.machineblocks.teleporter;

import universalelectricity.extend.BlockMachine;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

public abstract class BlockMachineTeleporter extends BlockMachine implements ITeleportationManager 
{
	public int timeToTeleport;
	
	public BlockMachineTeleporter(String name, int id, Material material, Teleporter teleporter) 
	{
		super(name, id, material);
	}

	@Override
	public abstract void onTeleport();
	
	@Override
	public abstract int timeToTeleportation();
}
