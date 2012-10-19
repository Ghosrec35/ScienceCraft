package net.sciencecraft.src.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

public class BlockIridiumOre extends Block 
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockIridiumOre(int i, int j)
	{
		super(i, j, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
