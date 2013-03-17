package net.sciencecraft.src.machineblocks.teleporter;

import net.minecraft.block.material.Material;

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
