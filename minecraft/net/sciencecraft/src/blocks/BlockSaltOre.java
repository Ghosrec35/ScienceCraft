package net.sciencecraft.src.blocks;

import java.util.Random;


import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.sciencecraft.src.ScienceCraft;

public class BlockSaltOre extends Block 
{
	public BlockSaltOre(int par1, int par2) 
	{
		super(par1, par2, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public int idDropped(int i, Random random, int j)
	{
		return this.blockID;
	}
	
	public String getTextureFile()
	{
		return ScienceCraft.SCDirectory + ScienceCraft.SCItemFile;
	}
}
