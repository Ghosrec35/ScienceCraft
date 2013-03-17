package net.sciencecraft.src.machineblocks.dnasequencer;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDNASequencer extends BlockMachine
{
	public BlockDNASequencer(int id, boolean active) 
	{
		super("DNASequencer", id, Material.rock);
	}
	
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityDNASequencer();
	}
}
