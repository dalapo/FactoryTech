package dalapo.factech.gui;

import java.awt.Point;

import dalapo.factech.gui.widget.WidgetFluidTank;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.MachineInfoRequestPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidMachine extends GuiBasicMachine {

	TileEntityFluidMachine machine;
	private int tankX;
	private int tankY;
	private int tankWidth;
	private int tankHeight;
	
	public GuiFluidMachine(ContainerBasicMachine inventorySlotsIn, IInventory player, TileEntityMachine te) {
		this(inventorySlotsIn, player, "basicmachine", te);
	}
	
	public GuiFluidMachine(ContainerBasicMachine inventorySlotsIn, IInventory player, String filename, TileEntityMachine te)
	{
		super(inventorySlotsIn, player, filename, te);
		machine = (TileEntityFluidMachine)getMachine();
		tankX = 26;
		tankY = 66;
		tankWidth = 16;
		tankHeight = 3;
	}
	
	public GuiFluidMachine setCoords(int x, int y)
	{
		tankX = x;
		tankY = y;
		return this;
	}
	
	public GuiFluidMachine setSize(int w, int h)
	{
		tankWidth = w;
		tankHeight = h;
		return this;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		addWidget(new WidgetFluidTank(this, tankX, tankY, tankWidth, tankHeight, machine.getTank(0)));
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		FluidStack fs = machine.getTankContents(0);
		if (fs == null || fs.getFluid() == null)
		{
//			Logger.info("Empty tank");
			return;
		}
	}
}