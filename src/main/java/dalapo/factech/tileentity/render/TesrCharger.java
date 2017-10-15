package dalapo.factech.tileentity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.FacTesrHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.specialized.TileEntityCoreCharger;

public class TesrCharger extends TesrMachine<TileEntityCoreCharger>
{

	public TesrCharger(boolean directional) {
		super(directional);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getModelName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRender(TileEntityCoreCharger te, double x, double y,
			double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.03125, 0.09375, -0.375);
		GlStateManager.scale(3.0/16.0, 3.0/16.0, 1);
		if (te.getInput(0).getItem() == ItemRegistry.coreUnfinished || (te.getInput(0).getItem() == ItemRegistry.machinePart && te.getInput().getItemDamage() == PartList.CORE.getFloor()))
		{
			FacTesrHelper.renderStack(te.getInput(0));
		}
		GlStateManager.popMatrix();
	}
}