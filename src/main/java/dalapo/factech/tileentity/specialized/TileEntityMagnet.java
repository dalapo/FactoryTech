package dalapo.factech.tileentity.specialized;

import static dalapo.factech.FactoryTech.random;
import dalapo.factech.FactoryTech;
import dalapo.factech.auxiliary.IInfoPacket;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.MagnetPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class TileEntityMagnet extends TileEntityBase implements ITickable, IInfoPacket {

	private short fieldStrength;
	private short cooldown;
	private boolean lastPower[] = new boolean[8];
	private int age = 0;
	
	public short getStrength()
	{
		return fieldStrength;
	}
	
	public short getCooldown()
	{
		return cooldown;
	}
	
	public void setFields(short str, short cd)
	{
		fieldStrength = str;
		cooldown = cd;
	}
	
	@Override
	public void onLoad()
	{
		PacketHandler.sendToAll(new MagnetPacket(this));
	}
	
	@Override
	public void sendInfoPacket(EntityPlayer ep) {
		MagnetPacket packet = new MagnetPacket(this);
		PacketHandler.sendToPlayer(packet, (EntityPlayerMP)ep);
	}

	@Override
	public void update()
	{
		if (FacMiscHelper.hasACPower(world, pos, lastPower) && cooldown == 0)
		{
			if (fieldStrength < 1024)
			{
				fieldStrength += random.nextInt(4);
			}
			else
			{
				if (!world.isRemote)
				{
					world.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);
				}
				else
				{
					for (int i=0; i<8; i++)
					{
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()+random.nextFloat(), pos.getY()+random.nextFloat()+0.5, pos.getZ()+random.nextFloat(), 0, 0, 0);
					}
				}
				fieldStrength = 0;
				cooldown = 256;
			}
		}
		else if (fieldStrength > 0)
		{
			fieldStrength -= random.nextInt(8);
			if (fieldStrength < 0) fieldStrength = 0;
		}
		
		if (cooldown > 0) cooldown--;
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("strength", fieldStrength);
		nbt.setInteger("cooldown", cooldown);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("strength")) fieldStrength = nbt.getShort("strength");
		if (nbt.hasKey("cooldown")) cooldown = nbt.getShort("cooldown");
	}

}