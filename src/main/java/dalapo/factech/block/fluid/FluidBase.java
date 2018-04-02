package dalapo.factech.block.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidBase extends Fluid {

	public FluidBase(String fluidName)
	{
		super(fluidName, new ResourceLocation("factorytech", String.format("blocks/fluid/%s_still", fluidName)), new ResourceLocation("factorytech", String.format("blocks/fluid/%s_flowing", fluidName)));
	}

	@Override
	public int getColor()
	{
		return 0xFFFFFFFF;
	}
}
