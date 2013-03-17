package net.sciencecraft.src.machineblocks.catalyzer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiCatalyzer extends GuiContainer 
{
	private TileEntityCatalyzer catalyzerInventory;
	
	public GuiCatalyzer(InventoryPlayer inventory, TileEntityCatalyzer catalyzer) 
	{
		super(new ContainerCatalyzer(inventory, catalyzer));
		this.catalyzerInventory = catalyzer;
	}

	protected void drawGuiContainerForegroundLayer()
	{
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.catalyzer"), 60, 6, 4210752);
        this.fontRenderer.drawString("Catalyzing:", 10, 28, 4210752);
        this.fontRenderer.drawString("Battery:", 10, 53, 4210752);
		
		
		String displayText = "";
		
		if(this.catalyzerInventory.isDisabled())
		{
			displayText = "Disabled!";
		}
		else
		if(this.catalyzerInventory.catalyzingTicks > 0)
		{
			displayText = "Ready";
		}
		else
		{
			displayText = "Idle";
		}
        this.fontRenderer.drawString("Status: " + displayText, 90, 48, 4210752);
        this.fontRenderer.drawString("Voltage: " + (int)this.catalyzerInventory.getVoltage(), 89, 60, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) 
	{
		int texture = this.mc.renderEngine.getTexture("/gui/furnace.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int xCoord = (this.width - this.xSize) / 2;
		int yCoord = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(xCoord, yCoord, 0, 0, this.xSize, this.ySize);
		
		if(this.catalyzerInventory.isRunning())
		{
			int time = ((this.catalyzerInventory.catalyzingTicks / this.catalyzerInventory.catalyzingTimeRequired) * 23);
			this.drawTexturedModalRect(xCoord + 56, yCoord + 36 + 12 - time, 176, 12 - time, 14, time);
		}
	}
}
