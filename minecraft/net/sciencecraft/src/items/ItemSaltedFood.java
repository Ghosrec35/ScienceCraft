package net.sciencecraft.src.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.sciencecraft.src.ScienceCraft;

public class ItemSaltedFood extends ItemFood 
{
	public ItemSaltedFood(int i, int j, float f, boolean bool) 
	{
		super(i, j, f, bool);
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public String getTextureFile()
	{
		return ScienceCraft.SCDirectory + ScienceCraft.SCItemFile;
	}
}
