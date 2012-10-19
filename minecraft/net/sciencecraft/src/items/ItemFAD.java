package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemFAD extends ItemEnergyMolecules 
{
	public ItemFAD(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("Flavin Adenine Dinucleotide");
	}
}
