package net.sciencecraft.src.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAlkaliMetal extends Block
{
	public final int explosionIntensity;
	
	public BlockAlkaliMetal(int blockID, Material blockMaterial, int explosionIntensity)
	{
		super(blockID, blockMaterial);
		this.explosionIntensity = explosionIntensity;
	}
	
	public BlockAlkaliMetal(int blockID, Material blockMaterial)
	{
		this(blockID, blockMaterial, 1);
	}
}
