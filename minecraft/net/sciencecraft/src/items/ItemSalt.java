package net.sciencecraft.src.items;

import net.minecraft.item.Item;
import net.sciencecraft.src.ScienceCraft;

public class ItemSalt extends Item
{
	public ItemSalt(int i)
	{
		super(i);
	}
	
	public String getTextureFile()
	{
		return ScienceCraft.SCDirectory + ScienceCraft.SCItemFile;
	}
}
