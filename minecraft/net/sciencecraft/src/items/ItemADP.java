package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemADP extends ItemEnergyMolecules 
{
	public ItemADP(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("A low energy version of ATP");
	}
}
