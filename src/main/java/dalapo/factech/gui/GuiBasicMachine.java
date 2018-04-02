package dalapo.factech.gui;

import java.awt.Point;

import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.MachineInfoRequestPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class GuiBasicMachine extends GuiFacInventory {

	String name;
	protected TileEntityMachine te;
	IInventory playerInv;
	private int barX;
	private int barY;
	
	public GuiBasicMachine(ContainerBasicMachine inventorySlotsIn, IInventory player, String id, TileEntityMachine te) {
		super(inventorySlotsIn);
		this.name = id;
		this.te = te;
		this.playerInv = player;
	}
	
	@Override
	public TileEntityBasicInventory getTileEntity()
	{
		return te;
	}
	
	public GuiBasicMachine(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te)
	{
		this(inventorySlotsIn, player, "basicmachine", te);
	}
	
	protected TileEntityMachine getMachine()
	{
		return te;
	}

	@Override
	public void initGui()
	{
		super.initGui();
//		PacketHandler.sendToServer(new MachineInfoRequestPacket(te.getPos()));
	}
	
	protected void drawInvSquare(int x, int y)
	{
		this.drawTexturedModalRect(guiLeft + x, guiTop + y, 176, 11, 18, 18);
	}
	
	// Trigger warning: Awful code
	// Experienced MC modders should avert their eyes
	protected Point getPartLocation(PartList part)
	{
//		Logger.info(part.getName());
		switch (part)
		{
		case BATTERY:
			return new Point(0, 0);
		case BLADE:
			return new Point(15, 0);
		case CIRCUIT_0:
			return new Point(45, 0);
		case CIRCUIT_1:
			return new Point(60, 0);
		case CIRCUIT_2:
			return new Point(75, 0);
		case CIRCUIT_3:
			return new Point(90, 0);
		case CORE:
			return new Point(105, 0);
		case DRILL:
			return new Point(120, 0);
		case GEAR:
			return new Point(135, 0);
		case HEATELEM:
			return new Point(150, 0);
		case MAGNET:
			return new Point(165, 0);
		case MESH:
			return new Point(180, 0);
		case MIXER:
			return new Point(195, 0);
		case MOTOR:
			return new Point(210, 0);
		case PISTON:
			return new Point(225, 0);
		case SAW:
			return new Point(240, 0);
		case SHAFT:
			return new Point(0, 15);
		case WIRE:
			return new Point(15, 15);
		case LENS:
			return new Point(30, 15);
		default:
			return new Point(256, 256);
		}
	}
			
		/*
		switch (part)
		{
		case WIRE:
			return new Point(176, 47);
		case GEAR:
			return new Point(194, 47);
		case PISTON:
			return new Point(176, 65);
		case DRILL:
			return new Point(194, 65);
		case SHAFT:
			return new Point(176, 83);
		case CIRCUIT_0:
		case CIRCUIT_1:
		case CIRCUIT_2:
		case CIRCUIT_3:
			return new Point(194, 83);
		case MOTOR:
			return new Point(176, 101);
		case BLADE:
			return new Point(194, 101);
		case HEATELEM:
			return new Point(176, 119);
		case BATTERY:
			return new Point(194, 119);
		case SAW:
			return new Point(176, 137);
		case MIXER:
			return new Point(194, 137);
			default:
				return new Point(0, 0);
		}
		*/
	
	public GuiBasicMachine setBarCoords(int barX, int barY)
	{
		this.barX = barX;
		this.barY = barY;
		return this;
	}
	
	protected void drawProgressBar()
	{
		int progress = te.getProgressScaled(21);
		this.drawTexturedModalRect(guiLeft + barX, guiTop + barY, 176, 0, progress, 11);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		this.mc.getTextureManager().bindTexture(new ResourceLocation(FacGuiHelper.formatTexName(name)));
		
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		drawProgressBar();
		
		for (int i=0; i<te.countPartSlots(); i++)
		{
			this.drawTexturedModalRect(guiLeft + 151, guiTop + 7 + (i*18), 176, 11, 18, 18);
		}
		
		for (int i=0; i<te.countPartSlots(); i++)
		{
			ResourceLocation itemSprite = new ResourceLocation(FacGuiHelper.formatTexName("part_spritesheet"));
			this.mc.getTextureManager().bindTexture(itemSprite);
			Point p = getPartLocation(te.getPartID(i));
//			Logger.info(String.format("%s, %s", p.x, p.y));
			this.drawTexturedModalRect(guiLeft + 134, guiTop + 8 + (i*18), p.x, p.y, 16, 16);
			
		}
		
		for (int i=0; i<te.countPartSlots(); i++)
		{
//			if (!((ContainerBasicMachine)inventorySlots).partsGot[i])
			if (!te.hasPart(i))
			{
				drawRect(guiLeft + 134, guiTop + 7 + (i*18), guiLeft + 152, guiTop + 25 + (i*18), 0x80FF0000);
			}
		}
		
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String str = this.te.getDisplayName().getUnformattedText();
		fontRenderer.drawString(str, 88 - fontRenderer.getStringWidth(str) / 2, 6, 0x404040);
	}
}