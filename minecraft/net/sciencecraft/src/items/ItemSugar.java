package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class ItemSugar extends Item 
{
	public ItemSugar(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.refinedSugar.itemID)
		{
			list.add("Sucrose refined from Sugar Cane. Made from Glucose and Fructose");
		}
		if(itemstack.itemID == ScienceCraft.registry.lactoseSugar.itemID)
		{
			list.add("Lactose refined from Milk. Made from Glucose and Galactose");
		}
		if(itemstack.itemID == ScienceCraft.registry.glucose.itemID)
		{
			list.add("Gluclose derived from Sucrose or Lactose");
		}
		if(itemstack.itemID == ScienceCraft.registry.fructose.itemID)
		{
			list.add("Fructose derived from Sucrose");
		}
		if(itemstack.itemID == ScienceCraft.registry.galactose.itemID)
		{
			list.add("Galactose derived from Lactose");
		}
	}
}
