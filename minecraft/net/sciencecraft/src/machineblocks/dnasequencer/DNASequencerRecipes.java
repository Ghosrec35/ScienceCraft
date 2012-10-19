package net.sciencecraft.src.machineblocks.dnasequencer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;

public class DNASequencerRecipes 
{
	private static final DNASequencerRecipes sequencingBase = new DNASequencerRecipes();
	
	private Map sequencerMap = new HashMap();
	private Map experienceList = new HashMap();
	private Map metaSequencerMap = new HashMap();
	
	public static final DNASequencerRecipes sequencing()
	{
		return sequencingBase;
	}
	
	public DNASequencerRecipes()
	{
		
	}
	
	public void addDNASequence(int ingredient, int ingredient1, int ingredient2, int ingredient3, int ingredient4, int ingredient5, int ingredient6, int ingredient7, int ingredient8, int ingredient9, int ingredient10, int ingredient11, ItemStack product, float experience)
	{
		sequencerMap.put
		(
			Arrays.asList
				(
					Integer.valueOf(ingredient),
					Integer.valueOf(ingredient1),
					Integer.valueOf(ingredient2),
					Integer.valueOf(ingredient3),
					Integer.valueOf(ingredient4),
					Integer.valueOf(ingredient5),
					Integer.valueOf(ingredient6),
					Integer.valueOf(ingredient7),
					Integer.valueOf(ingredient8),
					Integer.valueOf(ingredient9),
					Integer.valueOf(ingredient10),
					Integer.valueOf(ingredient11)),
					product
				);
		
		experienceList.put(Integer.valueOf(product.itemID), Float.valueOf(experience));
	}

	public ItemStack getSequencingResult(ItemStack[] sequencerStacks) 
	{
		for(int i = 0; i < 1 + (sequencerStacks.length); i++)
		{
			if(sequencerStacks[i] == null)
			{
				return null;
			}
		}
		ItemStack arrayOfItems = (ItemStack)sequencerMap.get
				(
					Arrays.asList
					(
						Integer.valueOf(sequencerStacks[1].itemID),
						Integer.valueOf(sequencerStacks[2].itemID),
						Integer.valueOf(sequencerStacks[3].itemID),
						Integer.valueOf(sequencerStacks[4].itemID),
						Integer.valueOf(sequencerStacks[5].itemID),
						Integer.valueOf(sequencerStacks[6].itemID),
						Integer.valueOf(sequencerStacks[7].itemID),
						Integer.valueOf(sequencerStacks[8].itemID),
						Integer.valueOf(sequencerStacks[9].itemID),
						Integer.valueOf(sequencerStacks[10].itemID),
						Integer.valueOf(sequencerStacks[11].itemID),
						Integer.valueOf(sequencerStacks[12].itemID)
					)
				);
		if(arrayOfItems != null)
		{
			return arrayOfItems;
		}
		return null;
	}
}
