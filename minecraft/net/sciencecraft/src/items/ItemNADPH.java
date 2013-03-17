package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.ItemStack;

public class ItemNADPH extends ItemEnergyMolecules 
{
	public ItemNADPH(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("Nicotinamide Adenine Dinucleotide Phosphate-Oxidase");
	}
}
