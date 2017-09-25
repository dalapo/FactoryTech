package dalapo.factech.gui;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.packet.ItemRedisPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiItemRedis extends GuiScreen {
	String texName;
	TileEntityItemRedis tile;
	GuiButton toggleSplit;
	GuiButton[][] buttons = new GuiButton[5][2];
	GuiLabel[] labels = new GuiLabel[5];
	private int left;
	private int top;
	private int xSize = 176;
	private int ySize = 166;
	
	public GuiItemRedis(String texName, TileEntityItemRedis te)
	{
		this.texName = texName;
		this.tile = te;	
	}
	
	public void initGui()
	{
		// Send a dummy packet to load the correct GUI values
		PacketHandler.sendToServer(new ItemRedisPacket(tile, 0, 0, false));
		left = (width - xSize) / 2;
		top = (height - ySize) / 2;
		for (int i=0; i<5; i++)
		{
			buttons[i][0] = new GuiButton(i, width / 2 - 30, height / 2 + (i*20) - 40, 20, 20, "<");
			buttons[i][1] = new GuiButton(i, width / 2 + 10, height / 2 + (i*20) - 40, 20, 20, ">");
			
			buttonList.add(buttons[i][0]);
			buttonList.add(buttons[i][1]);
		}
		toggleSplit = new GuiButton(0, width / 2 - 60, height / 2 - 70, 120, 20, "Toggle stack splitting");
		buttonList.add(toggleSplit);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
//		Logger.info("Called drawScreen");
		mc.getTextureManager().bindTexture(new ResourceLocation(FacGuiHelper.formatTexName("distributor")));
		drawDefaultBackground();
		this.drawTexturedModalRect(left, top, 0, 0, 176, 166);
		for (int i=0; i<5; i++)
		{
//			Logger.info(tile.getRatio(i));
			String str = Integer.toString(tile.getRatio(i));
			fontRenderer.drawString(str, left + 92 - fontRenderer.getStringWidth(str), top + 49 + i*20, 0);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
//		tile = (TileEntityItemRedis)tile.getWorldObj().getTileEntity(tile.xCoord, tile.yCoord, tile.zCoord);
		/*
		for (int i=0; i<5; i++)
		{
			Logger.info(tile.getRatio(i));
		}
		*/
		int change = 0;
		int side = 0;
		boolean flag = false;
		for (int i=0; i<buttons.length; i++)
		{
			for (int j=0; j<buttons[0].length; j++)
			{
				if (button == buttons[i][j])
				{
					flag = true;
					side = i;
					change = (j == 0 ? -1 : 1);
					break;
				}
			}
			if (flag) break;
		}
		if (button == toggleSplit) PacketHandler.sendToServer(new ItemRedisPacket(tile, 0, 0, true));
		else PacketHandler.sendToServer(new ItemRedisPacket(tile, side, change, false));
		tile.changeRatio(side, change);
		tile.markDirty();
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}