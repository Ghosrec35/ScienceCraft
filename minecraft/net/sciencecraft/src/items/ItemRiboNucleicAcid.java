package net.sciencecraft.src.items;

import java.util.List;


import net.minecraft.src.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class ItemRiboNucleicAcid extends ItemNucleicAcid 
{
	public ItemRiboNucleicAcid(int i) 
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.adenineRNA.shiftedIndex)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Adenine");
		}
		if(itemstack.itemID == ScienceCraft.registry.cytosineRNA.shiftedIndex)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Cytosine");
		}
		if(itemstack.itemID == ScienceCraft.registry.guanineRNA.shiftedIndex)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Guanine");
		}
		if(itemstack.itemID == ScienceCraft.registry.uracilRNA.shiftedIndex)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Uracil");
		}
	}
}
