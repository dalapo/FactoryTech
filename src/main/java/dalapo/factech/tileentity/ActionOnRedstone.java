package dalapo.factech.tileentity;

import net.minecraft.util.EnumFacing;

public interface ActionOnRedstone {
	public void onRedstoneSignal(boolean isSignal, EnumFacing side);
}