package dalapo.factech.auxiliary;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IFluidModel {
	@SideOnly(Side.CLIENT)
	public void registerModel();
}