package net.sciencecraft.src.machineblocks.teleporter;

import net.minecraft.block.material.Material;

public abstract class BlockSpecialTeleporter extends BlockMachineTeleporter implements ISpecialTeleporter
{
	public BlockSpecialTeleporter(String name, int id, Material material, Teleporter teleporter)
	{
		super(name, id, material, teleporter);
	}
}
