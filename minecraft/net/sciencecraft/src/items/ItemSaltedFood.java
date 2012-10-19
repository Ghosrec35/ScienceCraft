package net.sciencecraft.src.items;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemFood;
import net.sciencecraft.src.ScienceCraft;

public class ItemSaltedFood extends ItemFood 
{
	public ItemSaltedFood(int i, int j, float f, boolean bool) 
	{
		super(i, j, f, bool);
		this.setTabToDisplayOn(CreativeTabs.tabFood);
	}
	
	public String getTextureFile()
	{
		return ScienceCraft.SCDirectory + ScienceCraft.SCItemFile;
	}
}
