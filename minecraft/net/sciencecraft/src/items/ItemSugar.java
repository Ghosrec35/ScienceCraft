package net.sciencecraft.src.items;

import java.util.List;


import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class ItemSugar extends Item 
{
	public ItemSugar(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.refinedSugar.shiftedIndex)
		{
			list.add("Sucrose refined from Sugar Cane. Made from Glucose and Fructose");
		}
		if(itemstack.itemID == ScienceCraft.registry.lactoseSugar.shiftedIndex)
		{
			list.add("Lactose refined from Milk. Made from Glucose and Galactose");
		}
		if(itemstack.itemID == ScienceCraft.registry.glucose.shiftedIndex)
		{
			list.add("Gluclose derived from Sucrose or Lactose");
		}
		if(itemstack.itemID == ScienceCraft.registry.fructose.shiftedIndex)
		{
			list.add("Fructose derived from Sucrose");
		}
		if(itemstack.itemID == ScienceCraft.registry.galactose.shiftedIndex)
		{
			list.add("Galactose derived from Lactose");
		}
	}
}
