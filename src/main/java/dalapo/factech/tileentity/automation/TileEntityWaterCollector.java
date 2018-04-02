package dalapo.factech.tileentity.automation;

import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityWaterCollector extends TileEntityBase implements ITickable
{
	private FluidTank tank;
	private Biome curBiome;
	
	public TileEntityWaterCollector()
	{
		tank = new FluidTank(1000);
		tank.setTileEntity(this);
		tank.setCanFill(false);
	}
	private boolean isTouchingWater()
	{
		for (int i=0; i<6; i++)
		{
			if (world.getBlockState(FacMathHelper.withOffset(pos, EnumFacing.getFront(i))).getBlock().equals(Blocks.WATER))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing dir)
	{
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing dir)
	{
		if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		else return super.getCapability(cap, dir);
	}
	
	@Override
	public void update() {
		if (curBiome == null) curBiome = world.getBiome(pos);
		if ((!FacTechConfigManager.restrictWaterCollectorBiomes || (curBiome.equals(Biomes.OCEAN) || curBiome.equals(Biomes.DEEP_OCEAN) || curBiome.equals(Biomes.RIVER))) && isTouchingWater())
		{
			tank.fillInternal(new FluidStack(FluidRegistry.WATER, 100), true);
		}
	}
}