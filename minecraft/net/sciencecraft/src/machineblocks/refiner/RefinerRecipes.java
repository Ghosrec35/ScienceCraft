package net.sciencecraft.src.machineblocks.refiner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.sciencecraft.src.ScienceCraft;

public class RefinerRecipes 
{
	private static final RefinerRecipes refiningBase = new RefinerRecipes();
	
	private Map refiningList = new HashMap();
	private Map experienceList = new HashMap();
	private Map metaRefiningList = new HashMap();
	
	public static final RefinerRecipes refining()
	{
		return refiningBase;
	}
	
	private RefinerRecipes()
	{
		this.addRefining(ScienceCraft.registry.oreSalt.blockID, new ItemStack(ScienceCraft.registry.salt), 0.6F);
		this.addRefining(Item.sugar.shiftedIndex, new ItemStack(ScienceCraft.registry.refinedSugar), 0.4F);
		this.addRefining(Item.bucketMilk.shiftedIndex, new ItemStack(ScienceCraft.registry.lactoseSugar), 0.5F);
	}
	
	public void addRefining(int i, ItemStack itemstack, float f)
	{
		refiningList.put(Integer.valueOf(i), itemstack);
		experienceList.put(Integer.valueOf(itemstack.itemID), Float.valueOf(f));
	}
	
	public Map getRefiningList()
	{
		return refiningList;
	}
	
	public Map getMetaRefiningList()
	{
		return metaRefiningList;
	}
	
	public float func_77601_c(int i)
	{
		return this.experienceList.containsKey(Integer.valueOf(i)) ? ((Float)this.experienceList.get(Integer.valueOf(i))).floatValue() : 0.0F;
	}
	
	public void addRefining(int itemID, int metadata, ItemStack itemstack)
	{
		metaRefiningList.put(Arrays.asList(itemID, metadata), itemstack);
	}
	
	public ItemStack getRefiningResult(ItemStack itemstack)
	{
		if(itemstack == null)
		{
			return null;
		}
		ItemStack ret = (ItemStack)metaRefiningList.get(Arrays.asList(itemstack.itemID, itemstack.getItemDamage()));
		if(ret != null)
		{
			return ret;
		}
		return (ItemStack)refiningList.get(Integer.valueOf(itemstack.itemID));
	}
}
