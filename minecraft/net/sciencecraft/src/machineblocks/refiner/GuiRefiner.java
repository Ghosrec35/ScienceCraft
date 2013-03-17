package net.sciencecraft.src.machineblocks.refiner;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiRefiner extends GuiContainer 
{
	private TileEntityRefiner refinerInventory;
	
	public GuiRefiner(InventoryPlayer inventory, TileEntityRefiner refiner) 
	{
		super(new ContainerRefiner(inventory, refiner));
		this.refinerInventory = refiner;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() 
	{
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.refiner"), 60, 6, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		String displayText = "";
		
        if (this.refinerInventory.isDisabled())
        {
            displayText = "Disabled!";
        }
        else 
        if (this.refinerInventory.refiningTicks > 0)
        {
            displayText = "Ready";
        }
        else
        {
            displayText = "Idle";
        }
        this.fontRenderer.drawString("Status: " + displayText, 90, 68, 4210752);
        this.fontRenderer.drawString("Voltage: " + (int)this.refinerInventory.getVoltage(), 89, 80, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		//Replace later
		int texture = this.mc.renderEngine.getTexture("/gui/furnace.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int xCoord = (this.width - this.xSize) / 2;
		int yCoord = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xCoord, yCoord, 0, 0, this.xSize, this.ySize);
		
		if(this.refinerInventory.isRunning())
		{
			int time = ((this.refinerInventory.refiningTicks / this.refinerInventory.refiningTimeRequired) * 23);
			this.drawTexturedModalRect(xCoord + 56, yCoord + 36 + 12 - time, 176, 12 - time, 14, time);
		}
	}
}
