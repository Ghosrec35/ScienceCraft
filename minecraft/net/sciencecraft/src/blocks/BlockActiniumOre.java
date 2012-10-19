package net.sciencecraft.src.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockActiniumOre extends Block 
{
	private int counter = 0;
	
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockActiniumOre(int i, int j)
	{
		super(i, j, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
