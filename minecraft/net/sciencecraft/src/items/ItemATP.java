package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.ItemStack;

public class ItemATP extends ItemEnergyMolecules 
{
	public ItemATP(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("Adenosine Triphosphate, the primary energy carrier of life!");
	}
}
