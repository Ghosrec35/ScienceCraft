package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class ItemSoda extends ItemFood 
{
	public ItemSoda(int par1, int par2, float par3, boolean par4) 
	{
		super(par1, par2, par3, par4);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.rootBeerSoda.itemID)
		{
			list.add("Root Beer Soda made from Fructose, Water, and other ingredients...");
		}
		if(itemstack.itemID == ScienceCraft.registry.grapeSoda.itemID)
		{
			list.add("Grape Soda made from Fructose, Water, Grapes, and other ingredients...");
		}
		if(itemstack.itemID == ScienceCraft.registry.cherrySoda.itemID)
		{
			list.add("Cherry Soda made from Fructose, Water, Cherries, and other ingredients...");
		}
	}
}
