package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemLipid extends Item 
{
	public ItemLipid(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("A Lipid (fat) made from Fatty acids and Glycerol");
	}
}
