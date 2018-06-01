package dalapo.factech.tileentity.automation;

import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityPulser extends TileEntityBase implements ActionOnRedstone, ITickable
{
	private boolean isPulsing;
	private boolean isPowered;
	private int remainingPulses;
	private int remainingWait;
	private boolean currentState;
	
	private int ticksPer;
	private int ticksBetween;
	private int numPulses;
	
	@Override
	public void onRedstoneSignal(boolean isSignal, EnumFacing side)
	{
			if (world.isBlockPowered(pos))
			{
				if (!isPowered && isSignal)
				{
					beginPulseSequence();
					isPowered = true;
				}
			}
			else if (!isSignal) isPowered = false;
	}

	@Override
	public void update()
	{
		if (isPulsing)
		{
			if (remainingWait == 0)
			{
				if (!currentState) remainingPulses--;
				remainingWait = 2 * togglePulse(); // 1 Redstone tick = 2 game ticks
			}
			if (remainingPulses == 0) isPulsing = false;
		}
	}
	
	private int togglePulse()
	{
		currentState = !currentState;
		return currentState ? ticksPer : ticksBetween;
	}
	
	private void beginPulseSequence()
	{
		remainingPulses = numPulses;
		isPulsing = true;
	}
}