package net.sciencecraft.src.machineblocks.dnasequencer;

import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import universalelectricity.extend.BlockMachine;

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
