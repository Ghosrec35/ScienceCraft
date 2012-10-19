package net.sciencecraft.src.blocks;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class BlockSiliconOre extends Block 
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockSiliconOre(int i, int j)
	{
		super(i, j, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this));
	}
}
