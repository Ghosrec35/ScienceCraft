package net.sciencecraft.src.items;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.sciencecraft.src.ScienceCraft;


public class ItemDeoxyriboNucleicAcid extends ItemNucleicAcid 
{
	public ItemDeoxyriboNucleicAcid(int i)
	{
		super(i);
	}
	
	public void addInformation(ItemStack itemstack, List list)
	{
		if(itemstack.itemID == ScienceCraft.registry.adenine.itemID)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Adenine");
		}
		if(itemstack.itemID == ScienceCraft.registry.cytosine.itemID)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Cytosine");
		}
		if(itemstack.itemID == ScienceCraft.registry.guanine.itemID)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Guanine");
		}
		if(itemstack.itemID == ScienceCraft.registry.thymine.itemID)
		{
			list.add("A DNA Nucleotide created with the Nitrogenous Base Thymine");
		}
	}
}
