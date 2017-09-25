package dalapo.factech.gui.widget;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import dalapo.factech.gui.GuiFacInventory;
import dalapo.factech.helper.FacFluidRenderHelper;
import dalapo.factech.helper.FacGuiHelper;
import dalapo.factech.helper.Logger;

/**
 * Simple fluid tank widget
 * Does not handle packets; relies on the parent TE to do that
 * 
 * 
 */
public class WidgetFluidTank extends FacTechWidget {

	private final FluidTank tank;
	
	public WidgetFluidTank(GuiFacInventory parent, int x, int y, int w, int h, FluidTank tank) {
		super(parent, x, y, w, h);
		this.tank = tank;
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
		
		getParent().drawTexturedModalRect(x + parX, y + parY + height - (int)((height * ratio) * 16), fluidSprite, width, (int)((height * ratio) * 16));
	}
}