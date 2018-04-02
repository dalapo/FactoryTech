package dalapo.factech.tileentity.specialized;

import java.util.Arrays;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.StabilizerPacket;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityStabilizer extends TileEntityMachine {

	private short stability = 0;
	private int dS = 0; // Strangely enough, S represents the exact opposite of entropy in this case.
	short[] magnetStrengths = new short[4];
	boolean[] isCooling = new boolean[4];
	
	public TileEntityStabilizer() {
		super("stabilizer", 1, 0, 1, RelativeSide.BOTTOM);
		setDisplayName("");
	}
	
	@Override
	public void syncToAll()
	{
		super.syncToAll();
		StabilizerPacket packet = new StabilizerPacket(this);
		PacketHandler.sendToAll(packet);
	}

	@Override
	protected void fillMachineParts()
	{
	}
	
	public boolean isCooling(int side)
	{
		return isCooling[side];
	}
	
	public short getStrength(int side)
	{
		return magnetStrengths[side];
	}
	
	public short getStability()
	{
		return stability;
	}
	
	public void updateValues(short stb, short[] str, boolean[] cool)
	{
		stability = stb;
		for (int i=0; i<4; i++)
		{
			magnetStrengths[i] = str[i];
			isCooling[i] = cool[i];
		}
	}
	
	@Override
	public void getHasWork()
	{
		hasWork = getInput().getItem().equals(ItemRegistry.coreUnfinished) && getInput().getItemDamage() < 40;
	}
	
	@Override
	public boolean canRun()
	{
		return true;
	}
	
	@Override
	protected boolean performAction() {
		for (int i=2; i<6; i++)
		{
			TileEntity te = world.getTileEntity(FacMathHelper.withOffsetAndDist(pos, EnumFacing.getFront(i), 3));
			if (te instanceof TileEntityMagnet)
			{
				magnetStrengths[i-2] = ((TileEntityMagnet)te).getStrength();
				isCooling[i-2] = ((TileEntityMagnet)te).getCooldown() > 0;
			}
			else
			{
				magnetStrengths[i-2] = -1;
			}
		}
		if (!getInput().getItem().equals(ItemRegistry.coreUnfinished) || getInput().getItemDamage() > 40)
		{
			dS = 0;
		}
		else
		{
			double average = 0;
			int lowest = magnetStrengths[0];
			int highest = magnetStrengths[0];
			short[] tempStrengths = new short[4];
			for (int i=0; i<4; i++)
			{
				short strength = magnetStrengths[i];
				if (strength < lowest) lowest = strength;
				if (strength > highest) highest = strength;
				average += strength / 4.0;
				tempStrengths[i] = magnetStrengths[i];
			}
			Arrays.sort(tempStrengths);
			int biggestJump = tempStrengths[1] - tempStrengths[0];
			for (int i=2; i<4; i++)
			{
				if (tempStrengths[i] - tempStrengths[i-1] > biggestJump)
				{
					biggestJump = tempStrengths[i] - tempStrengths[i-1];
				}
			}
			// Magnet factors
			dS = (int)((average * 4 - 2*(highest - lowest) - 2*(highest-average) - 2*(average-lowest) - biggestJump) / 100);
			dS *= 1.0 - (0.05 * Math.abs(getInput().getItemDamage()) - 20); // Maximize at 80% charge
//			Logger.info(String.format("dS: %s", dS));
		}
		stability += dS;
		if (stability <= -200)
		{
			setInventorySlotContents(0, ItemStack.EMPTY);
			world.createExplosion(null, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 4, true);
		}
		if (stability >= 500 && getInput().isItemEqualIgnoreDurability(new ItemStack(ItemRegistry.coreUnfinished)))
		{
			stability = 0;
			ItemStack is = new ItemStack(ItemRegistry.machinePart, 1, PartList.CORE.getFloor());
			if (!doOutput(is))
			{
				EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, is);
				world.spawnEntity(ei);
			}
			setInventorySlotContents(0, ItemStack.EMPTY);
		}
		FacBlockHelper.updateBlock(world, pos);
		return true;
	}

	@Override
	public int getOpTime() {
		// Unfortunately, frequent client-side syncing is indeed necessary.
		return 10;
	}

}
