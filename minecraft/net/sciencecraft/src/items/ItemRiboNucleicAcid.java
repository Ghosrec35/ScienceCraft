package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class ItemRiboNucleicAcid extends ItemNucleicAcid 
{
	public ItemRiboNucleicAcid(int i) 
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.adenineRNA.itemID)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Adenine");
		}
		if(itemstack.itemID == ScienceCraft.registry.cytosineRNA.itemID)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Cytosine");
		}
		if(itemstack.itemID == ScienceCraft.registry.guanineRNA.itemID)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Guanine");
		}
		if(itemstack.itemID == ScienceCraft.registry.uracilRNA.itemID)
		{
			list.add("A RNA Nucleotide created with the Nitrogenous Base Uracil");
		}
	}
}
