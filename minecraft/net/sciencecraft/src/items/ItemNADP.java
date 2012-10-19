package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.src.ItemStack;

public class ItemNADP extends ItemEnergyMolecules 
{
	public ItemNADP(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		list.add("Nicotinamide Adenine Dinucleotide Phosphate");
	}
}
