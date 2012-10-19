package net.sciencecraft.src.blocks;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;

public class BlockFranciumOre extends Block 
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockFranciumOre(int i, int j)
	{
		super(i, j, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
