package dalapo.factech.tileentity.render;

import net.minecraft.item.ItemStack;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.FacTesrHelper;
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
		if (te.getStackInSlot(0).getItem() == ItemRegistry.coreUnfinished)
		{
			FacTesrHelper.renderStack(new ItemStack(ItemRegistry.coreUnfinished, 0, 1));
		}
	}

}