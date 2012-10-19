package net.sciencecraft.src.machineblocks.catalyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class CatalyzerRecipes 
{
	private static CatalyzerRecipes catalyzingBase = new CatalyzerRecipes();
	
	private Map catalyzingList = new HashMap();
	private Map catalyzingList2 = new HashMap();
	private Map experienceList = new HashMap();
	private Map metaCatalyzingList = new HashMap();
	
	public static final CatalyzerRecipes catalyzing()
	{
		return catalyzingBase;
	}
	
	public CatalyzerRecipes()
	{
		this.addCatalyzationEvent(ScienceCraft.registry.refinedSugar.shiftedIndex, new ItemStack(ScienceCraft.registry.glucose), new ItemStack(ScienceCraft.registry.fructose), 0.5F);
		this.addCatalyzationEvent(ScienceCraft.registry.lactoseSugar.shiftedIndex, new ItemStack(ScienceCraft.registry.glucose), new ItemStack(ScienceCraft.registry.galactose), 0.5F);
	}
	
	public void addCatalyzationEvent(int id, ItemStack product, ItemStack product2, Float f)
	{
		catalyzingList.put(Integer.valueOf(id), Arrays.asList(product, product2));
		experienceList.put(Integer.valueOf(product.itemID), Float.valueOf(f));
	}
	
	public Map getCatalyzationEventList()
	{
		return catalyzingList;
	}
	
	public float func_77601_c(int i)
	{
		return this.experienceList.containsKey(Integer.valueOf(i)) ? ((Float)this.experienceList.get(Integer.valueOf(i))).floatValue() : 0.0F;
	}
	
	public void addCatalyzationEvent(int id, int metadata, ItemStack product, ItemStack product2)
	{
		metaCatalyzingList.put(Arrays.asList(id, metadata), Arrays.asList(product, product2));
	}
	
	public ItemStack[] getCatalyzationResult(ItemStack itemstack)
	{
		if(itemstack == null)
		{
			return null;
		}
		ItemStack[] ret = new ItemStack[2];
		ItemStack[] arrayOfProducts = (ItemStack[]) metaCatalyzingList.get(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()));;
		
		ret[0] = arrayOfProducts[0];
		ret[1] = arrayOfProducts[1];
		if(ret[0] != null && ret[1] != null)
		{
			return ret;
		}
		return (ItemStack[])catalyzingList.get(Integer.valueOf(itemstack.itemID));
	}
}
