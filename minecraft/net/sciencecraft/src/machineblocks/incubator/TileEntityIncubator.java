package net.sciencecraft.src.machineblocks.incubator;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemHoe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemSword;
import net.minecraft.src.ItemTool;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityIncubator extends TileEntity implements IInventory, ISidedInventory
{
	private ItemStack[] incubatorStacks = new ItemStack[3];
	
	public int incubatorWarmTime = 0;
	
	public int currentItemWarmTime = 0;
	
	public int incubatorFuelTime = 0;

	@Override
	public int getStartInventorySide(ForgeDirection side) 
	{
		if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0; 
        return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) 
	{
		return 1;
	}

	@Override
	public int getSizeInventory() 
	{
		return this.incubatorStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return this.incubatorStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) 
	{
		if (this.incubatorStacks[i] != null)
        {
            ItemStack itemstack;

            if (this.incubatorStacks[i].stackSize <= j)
            {
            	itemstack = this.incubatorStacks[i];
                this.incubatorStacks[i] = null;
                return itemstack;
            }
            else
            {
            	itemstack = this.incubatorStacks[i].splitStack(j);

                if (this.incubatorStacks[i].stackSize == 0)
                {
                    this.incubatorStacks[i] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) 
	{
		if (this.incubatorStacks[i] != null)
		{
			ItemStack itemstack = this.incubatorStacks[i];
			this.incubatorStacks[i] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		this.incubatorStacks[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() 
	{
		return "container.incubator";
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	
	public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbtlist = nbttagcompound.getTagList("IncubatorItems");
        this.incubatorStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbtlist.tagCount(); ++i)
        {
            NBTTagCompound nbttag = (NBTTagCompound)nbtlist.tagAt(i);
            byte nbtByte = nbttag.getByte("IncubatorSlot");

            if (nbtByte >= 0 && nbtByte < this.incubatorStacks.length)
            {
                this.incubatorStacks[nbtByte] = ItemStack.loadItemStackFromNBT(nbttag);
            }
        }

        this.incubatorWarmTime = nbttagcompound.getShort("WarmTime");
        this.incubatorFuelTime = nbttagcompound.getShort("FuelTime");
        this.currentItemWarmTime = getItemIncubateTime(this.incubatorStacks[1]);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("WarmTime", (short)this.incubatorWarmTime);
        nbttagcompound.setShort("FuelTime", (short)this.incubatorFuelTime);
        NBTTagList nbtlist = new NBTTagList();

        for (int i = 0; i < this.incubatorStacks.length; ++i)
        {
            if (this.incubatorStacks[i] != null)
            {
                NBTTagCompound nbttag = new NBTTagCompound();
                nbttag.setByte("IncubatorSlot", (byte)i);
                this.incubatorStacks[i].writeToNBT(nbttag);
                nbtlist.appendTag(nbttag);
            }
        }

        nbttagcompound.setTag("IncubatorItems", nbtlist);
    }

    @SideOnly(Side.CLIENT)
    public int getIncubatorProgressScaled(int i)
    {
    	return this.incubatorWarmTime * i / 200;
    }
    
    @SideOnly(Side.CLIENT)
    public int getWarmTimeRemainingScaled(int i)
    {
    	if(this.currentItemWarmTime == 0)
    	{
    		this.currentItemWarmTime = 7200;
    	}
    	
    	return this.incubatorWarmTime * i / this.currentItemWarmTime;
    }
    
    public boolean isRunning()
    {
    	return this.incubatorWarmTime > 0;
    }
    
    public void updateEntity()
    {
    	boolean bool = this.incubatorWarmTime > 0;
    	boolean bool1 = false;
    	
    	if(this.incubatorWarmTime > 0)
    	{
    		--this.incubatorWarmTime;
    	}
    	
    	if(!this.worldObj.isRemote)
    	{
    		if(this.incubatorWarmTime == 0 && this.canIncubate())
    		{
    			this.currentItemWarmTime = this.incubatorWarmTime = getItemIncubateTime(this.incubatorStacks[1]);
    			
    			if(this.incubatorWarmTime > 0)
    			{
    				bool1 = true;
    				
    				if(this.incubatorStacks[1] != null)
    				{
    					--this.incubatorStacks[1].stackSize;
    					
    					if(this.incubatorStacks[1].stackSize == 0)
    					{
    						this.incubatorStacks[1] = this.incubatorStacks[1].getItem().getContainerItemStack(this.incubatorStacks[1]);
    					}
    				}
    			}
    		}
    		
    		if(this.isRunning() && this.canIncubate())
    		{
    			++this.incubatorFuelTime;
    			if(this.incubatorFuelTime == 72000)
    			{
    				this.incubatorFuelTime = 0;
    				this.incubateItem();
    				bool1 = true;
    			}
    		}
    		else
    		{
    			this.incubatorFuelTime = 0;
    		}
    		
    		if(bool != this.incubatorWarmTime > 0)
    		{
    			bool1 = true;
    			BlockIncubator.updateIncubatorBlockState(this.incubatorWarmTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    		}
    	}
    	
    	if(bool1)
    	{
    		this.onInventoryChanged();
    	}
    }
    
    private boolean canIncubate()
    {
    	if(this.incubatorStacks[0] == null)
    	{
    		return false;
    	}
    	else
    	{
    		ItemStack itemstack = IncubatorRecipes.incubating().getIncubatingResult(this.incubatorStacks[0]);
    		if(itemstack == null){return false;}
    		if(this.incubatorStacks[2] == null){return false;}
    		if(!this.incubatorStacks[2].isItemEqual(itemstack)) {return false;}
    		int result = this.incubatorStacks[2].stackSize + itemstack.stackSize;
    		return (result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
    	}
    }
    
    private void incubateItem()
    {
    	if(this.canIncubate())
    	{
    		ItemStack itemstack = IncubatorRecipes.incubating().getIncubatingResult(this.incubatorStacks[0]);
    		
    		if(this.incubatorStacks[2] == null)
    		{
    			this.incubatorStacks[2] = itemstack.copy();
    		}
    		else
    		if(this.incubatorStacks[2].isItemEqual(itemstack))
    		{
    			this.incubatorStacks[2].stackSize += itemstack.stackSize;
    		}
    		
    		--this.incubatorStacks[0].stackSize;
    		
    		if(this.incubatorStacks[0].stackSize <= 0)
    		{
    		}
    	}
    }
    
    public static int getItemIncubateTime(ItemStack itemstack)
    {
    	if(itemstack == null)
    	{
    		return 0;
    	}
    	else
    	{
    		int itemID = itemstack.getItem().shiftedIndex;
    		Item item = itemstack.getItem();
    		
    		if(itemstack.getItem() instanceof ItemBlock && Block.blocksList[itemID] != null)
    		{
    			Block block = Block.blocksList[itemID];
    			
    			if(block == Block.woodSingleSlab)
    			{
    				return 400;
    			}
    			
    			if(block.blockMaterial == Material.wood)
    			{
    				return 800;
    			}
    		}
    		
            if (item instanceof ItemTool && ((ItemTool) item).func_77861_e().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword) item).func_77825_f().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe) item).func_77842_f().equals("WOOD")) return 200;
            if (itemID == Item.stick.shiftedIndex) return 200;
            if (itemID == Item.coal.shiftedIndex) return 3200;
            if (itemID == Item.bucketLava.shiftedIndex) return 40000;
            if (itemID == Block.sapling.blockID) return 800;
            if (itemID == Item.blazeRod.shiftedIndex) return 4800;
            return 0;
    	}
    }
    
    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemIncubateTime(par0ItemStack) > 0;
    }
    
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}
}
