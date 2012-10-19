package net.sciencecraft.src.items;

import java.util.List;


import net.minecraft.src.ItemStack;
import net.sciencecraft.src.ScienceCraft;


public class ItemDeoxyriboNucleicAcid extends ItemNucleicAcid 
{
	public ItemDeoxyriboNucleicAcid(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.adenine.shiftedIndex)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Adenine");
		}
		if(itemstack.itemID == ScienceCraft.registry.cytosine.shiftedIndex)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Cytosine");
		}
		if(itemstack.itemID == ScienceCraft.registry.guanine.shiftedIndex)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Guanine");
		}
		if(itemstack.itemID == ScienceCraft.registry.thymine.shiftedIndex)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Thymine");
		}
	}
}
