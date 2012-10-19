package net.sciencecraft.src.machineblocks.incubator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.ItemStack;

public class IncubatorRecipes 
{
	private static final IncubatorRecipes incubatorBase = new IncubatorRecipes();
	
	private Map incubatorMap = new HashMap();
	private Map experienceList = new HashMap();
	private Map metaIncubatorMap = new HashMap();
	
	public static final IncubatorRecipes incubating()
	{
		return incubatorBase;
	}
	
	public IncubatorRecipes()
	{
		
	}
	
	public void addIncubating(int id, ItemStack itemstack, Float f)
	{
		incubatorMap.put(Integer.valueOf(id), itemstack);
		experienceList.put(Integer.valueOf(itemstack.itemID), Float.valueOf(f));
	}
	
	public void addIncubating(int id, ItemStack itemstack, int metadata)
	{
		metaIncubatorMap.put(Integer.valueOf(id), Arrays.asList(itemstack, metadata));
	}
	
	public Map getIncubatingList()
	{
		return incubatorMap;
	}
	
	public Map getMetaIncubatingList()
	{
		return metaIncubatorMap;
	}
	
	public float func_77601_c(int i)
	{
		return this.experienceList.containsKey(Integer.valueOf(i)) ? ((Float)this.experienceList.get(Integer.valueOf(i))).floatValue() : 0.0F;
	}
	
	public ItemStack getIncubatingResult(ItemStack itemstack)
	{
		if(itemstack == null)
		{
			return null;
		}
		
		ItemStack ret = (ItemStack)metaIncubatorMap.get(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()));
		if(ret != null)
		{
			return ret;
		}
		return (ItemStack)incubatorMap.get(Integer.valueOf(itemstack.itemID));
	}
}
