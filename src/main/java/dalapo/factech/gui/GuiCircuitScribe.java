package dalapo.factech.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.CircuitScribePacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityCircuitScribe;

public class GuiCircuitScribe extends GuiBasicMachine {

	private TileEntityCircuitScribe scribe;
	
	// Data is stored in row/column format
	private boolean[][] board;
	private boolean isValidPattern = false;
	private final int boardLeft = 32;
	private final int boardTop = 98;
	
	public GuiCircuitScribe(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te) {
		super(inventorySlotsIn, player, "circscribe_gui", te);
		scribe = (TileEntityCircuitScribe)te;
		this.ySize = 200;
		
//		board = new boolean[5][6];
	}
	
	public static boolean[][] getBoard(int pattern)
	{
		switch (pattern)
		{
		case 0:
			return new boolean[][] {
					{false, true, false, true, false},
					{true, true, true, true, true},
					{false, false, true, false, true},
					{false, true, true, true, false},
					{true, true, false, true, true},
					{false, true, true, true, false}};
		case 1:
			return new boolean[][] {
					{false, false, true, false, false},
					{false, false, true, false, false},
					{false, true, true, true, false},
					{false, true, false, true, false},
					{true, true, true, true, true},
					{true, false, true, false, true}};
		case 2:	
			return new boolean[][] {
					{false, false, false, true, false},
					{false, true, true, true, false},
					{false, true, false, true, true},
					{true, true, false, true, false},
					{false, true, false, false, false},
					{false, true, true, true, false}};
		case 3:
			return new boolean[][] {
					{false, true, false, true, true},
					{true, true, true, true, false},
					{true, false, false, true, true},
					{true, true, true, true, false},
					{true, false, true, false, false},
					{true, false, true, false, false}};
			default:
				return new boolean[6][5];
		}
	}
	
	private byte getMatch()
	{
		for (byte i=0; i<4; i++)
		{
			boolean matches = true;
			boolean[][] boardTest = getBoard(i);
			if (boardTest.length != board.length || boardTest[0].length != board[0].length) return -1;
			for (int row=0; row<board.length; row++)
			{
				for (int col=0; col<board[row].length; col++)
				{
					if (board[row][col] != boardTest[row][col]) matches = false;
				}
			}
			if (matches) return i;
		}
		return -1;
	}
	
	@Override
	public void drawProgressBar()
	{
		int progress = scribe.getProgressScaled(21);
		this.drawTexturedModalRect(guiLeft + 11, guiTop + 47, 194, 0, 14, progress);
	}
	@Override
	public void initGui()
	{
		super.initGui();
		board = getBoard(scribe.getPattern());
		isValidPattern = getMatch() != -1;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
//		GlStateManager.pushMatrix();
		for (int x=0; x<board.length*16; x+=16)
		{
			for (int y=0; y<board[x/16].length*16; y+=16)
			{
				if (board[x/16][y/16])
				{
					drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFF808080);
				}
				else
				{
					drawRect(boardLeft+x, boardTop-y, boardLeft+x+16, boardTop-y-16, 0xFFFFFFFF);
				}
			}
		}
//		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(FacGuiHelper.formatTexName(name)));
		if (isValidPattern)
		{
			drawTexturedModalRect(152, 90, 178, 52, 18, 12);
		}
		else
		{
			drawTexturedModalRect(152, 90, 178, 68, 18, 18);
		}
		GlStateManager.popMatrix();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int boardLeft = this.boardLeft + guiLeft;
		int boardTop = this.boardTop + guiTop;
//		if (FacMathHelper.isInRange(mouseX, 162, 256) && FacMathHelper.isInRange(mouseY, 34, 114))
		if (FacMathHelper.isInRange(mouseX, boardLeft, boardLeft + 95) && FacMathHelper.isInRange(mouseY, boardTop - 79, boardTop))
		{
//			Logger.info("Clicked the grid");
			int modX = mouseX - boardLeft;
			int modY = boardTop - mouseY;
			board[modX/16][modY/16] = !board[modX/16][modY/16];
		}
		byte match = getMatch();
		if (match != -1)
		{
			scribe.setPattern(match);
			isValidPattern = true;
			PacketHandler.sendToServer(new CircuitScribePacket(scribe));
		}
		else 
		{
			isValidPattern = false;
		}
		this.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		if (getMatch() == -1)
		{
			scribe.setPattern((byte)-1);
			PacketHandler.sendToServer(new CircuitScribePacket(scribe));
		}
	}
}