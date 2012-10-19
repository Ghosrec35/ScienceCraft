package net.sciencecraft.src.blocks;

import java.util.ArrayList;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockNeon extends Block
{
	public String getTextureFile()
	{
		return "/ElementsMod/Blocks.png";
	}
	
	public BlockNeon(int i, int j)
	{
		super(i, j, Material.air);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public boolean isAirBlock(World world, int x, int y, int z)
	{
		return true;
	}
	
	public void addCreativeItems(ArrayList itemList)
    {
    	itemList.add(new ItemStack(this));
    }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }
}
