package net.sciencecraft.src.blocks;

import java.util.ArrayList;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;

public class BlockBoronOre extends Block 
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockBoronOre(int i, int j)
	{
		super(i, j, Material.rock);
	}
	
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this));
	}
}
