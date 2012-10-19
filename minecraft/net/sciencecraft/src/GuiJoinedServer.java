package net.sciencecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;

public class GuiJoinedServer extends GuiScreen
{
	public void init()
	{
		controlList.clear();
		
		controlList.add(new GuiButton(100, width / 2 - 100, height / 7, width, height, "Continue"));
		controlList.add(new GuiButton(101, width / 2 + 100, height / 7, width, height, "Disconnect"));
	}
	
	public void drawBackground(int par1)
	{
		drawDefaultBackground();
	}
	
	public void drawScreen(int par1, int par2, float par3)
	{
		this.fontRenderer.drawStringWithShadow("Be aware that errors or glitches may occur when joining a server without ScienceCraft installed", this.width / 2 - 100, this.height / 2, 12);
		super.drawScreen(par1, par2, par3);
	}
	
	public void actionPerformed(GuiButton guibutton)
	{
		if(guibutton.id == 100)
		{
			onGuiClosed();
			Minecraft.getMinecraft().currentScreen = null;
		}
		
		if(guibutton.id == 101)
		{
			Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
			Minecraft.getMinecraft().currentScreen = new GuiMainMenu();
		}
	}
}
