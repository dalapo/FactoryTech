package dalapo.factech.gui.widget;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import dalapo.factech.auxiliary.IHasFluid;
import dalapo.factech.gui.GuiFacInventory;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.FluidTankEmptyPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.TileEntityBase;

/**
 * Simple fluid tank widget
 * Does not handle packets; relies on the parent TE to do that
 * 
 * 
 */
public class WidgetFluidTank extends FacTechWidget {

	private final FluidTank tank;
	private int tankID; // NOTE: This relies on the ID here matching the ID of the tank, which is obviously Very Bad (tm). 
	
	public WidgetFluidTank(GuiFacInventory parent, int x, int y, int w, int h, FluidTank tank, int tankID) {
		super(parent, x, y, w, h);
		this.tank = tank;
		this.tankID = tankID;
	}
	
	public void init()
	{
		// NO-OP
	}
	
	public void handle(int mouseButton, boolean shift)
	{
		if (shift)
		{
			TileEntityBase te = (TileEntityBase)parent.getTileEntity();
			if (te instanceof IHasFluid)
			{
				((IHasFluid) te).overrideTank(null, tankID);
				PacketHandler.sendToServer(new FluidTankEmptyPacket(te, tankID));
			}
		}
	}

	@Override
	public void draw() {
		FluidStack fluid = tank.getFluid();
		if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) return;
		TextureAtlasSprite fluidSprite = FacFluidRenderHelper.getSprite(fluid, false);
		if (fluidSprite == null) return;
		float ratio = (float)fluid.amount / tank.getCapacity();
		getParent().bindTextureManager(TextureMap.LOCATION_BLOCKS_TEXTURE);
		FacGuiHelper.setColor(fluid.getFluid().getColor());
		
		getParent().drawTexturedModalRect(x + parX, y + parY + 3 - (int)(height * ratio), fluidSprite, width, (int)(height * ratio));
	}

	@Override
	public String getTooltip()
	{
		if (tank.getFluidAmount() == 0) return "Empty";
		return tank.getFluid().getLocalizedName() + " ("+ tank.getFluidAmount() + " mB)";
	}
	
	@Override
	public boolean isPointInBounds(int x, int y)
	{
		return x >= this.x && x <= this.x + this.width && y >= this.y - this.height + 3 && y <= this.y + 3;
	}
}