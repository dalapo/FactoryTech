package dalapo.factech.tileentity.automation;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileEntityItemInterceptor extends TileEntityBasicInventory
{
	public TileEntityItemInterceptor()
	{
		super("interceptor", 9);
		setDisplayName("Interceptor");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public void invalidate()
	{
		if (!isInvalid())
		{
			super.invalidate();
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
	@SubscribeEvent
	public void captureItem(EntityJoinWorldEvent e)
	{
		Entity entity = e.getEntity();
		int maxDist = FacMathHelper.getMax(FacMathHelper.getMax(Math.abs((int)entity.posX-pos.getX()), Math.abs((int)entity.posY-pos.getY())), Math.abs((int)entity.posZ-pos.getZ()));
		if (!world.isRemote && entity instanceof EntityItem && !world.isBlockPowered(pos) && FacMathHelper.isInRangeInclusive(maxDist, 3, 12))
		{
			ItemStack is = ((EntityItem)entity).getItem();
			IItemHandler inv = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
			if (FacTileHelper.hasSpaceForItem(inv, is, EnumFacing.UP, false))
			{
				world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX, entity.posY, entity.posZ, 0, 0, 0);
				((EntityItem)entity).setItem(FacTileHelper.tryInsertItem(getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), is, EnumFacing.UP.ordinal()));
			}
		}
	}
}