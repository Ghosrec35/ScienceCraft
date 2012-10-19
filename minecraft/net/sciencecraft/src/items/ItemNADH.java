package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemNADH extends ItemEnergyMolecules 
{
	public ItemNADH(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("NADH Dehydrogenase used in Cellular Respiration");
	}
}
