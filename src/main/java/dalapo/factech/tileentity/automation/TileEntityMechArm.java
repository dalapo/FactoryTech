package dalapo.factech.tileentity.automation;

import java.lang.ref.WeakReference;

import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.ActionOnRedstone;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class TileEntityMechArm extends TileEntityBasicInventory implements ActionOnRedstone {
	private boolean isPowered = false;

	private FakePlayer fakePlayer;
	private WeakReference<FakePlayer> fake = new WeakReference(fakePlayer);
	
	public TileEntityMechArm()
	{
		super("mecharm", 1);
		setDisplayName("Mechanical Arm");
	}

	private void reloadPlayer()
	{
		if (fakePlayer == null)
		{
			fakePlayer = FacMiscHelper.getFakePlayer(world);
		}
		Vec3d playerPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ()).addVector(0.5, 0.5, 0.5);
		EnumFacing direction = world.getBlockState(pos).getValue(StateList.directions);
		float yaw = direction.getHorizontalAngle();
		fakePlayer.setLocationAndAngles(playerPos.x, playerPos.y, playerPos.z, 0, yaw);
	}
	
	private boolean tryUseItem(ItemStack is, BlockPos target)
	{
		RightClickBlock rcb = ForgeHooks.onRightClickBlock(fakePlayer, EnumHand.MAIN_HAND, target, EnumFacing.UP, new Vec3d(0.5, 0.5, 0.5));
		if (rcb.isCanceled() || rcb.getResult() == Result.DENY) return false;
		
		EnumActionResult first = is.onItemUseFirst(fakePlayer, world, target, EnumHand.MAIN_HAND, EnumFacing.UP, 0.5F, 0.5F, 0.5F);
		if (first == EnumActionResult.SUCCESS) return true;
		if (first == EnumActionResult.FAIL) return false;
		EnumActionResult next = is.onItemUse(fakePlayer, world, target, EnumHand.MAIN_HAND, EnumFacing.UP, 0.5F, 0.5F, 0.5F);
		if (next == EnumActionResult.SUCCESS) return true;
		if (next == EnumActionResult.FAIL) return false;
		
		return false;
	}
	@Override
	public void onRedstoneSignal(boolean isSignal) {
		if (world.isBlockPowered(pos))
		{
			if (!isPowered && isSignal)
			{
				EnumFacing direction = world.getBlockState(pos).getValue(StateList.directions);
				reloadPlayer();
				
				ItemStack is = getStackInSlot(0);
				if (!is.isEmpty())
				{
					fakePlayer.inventory.setInventorySlotContents(0, is);
					BlockPos target = FacMathHelper.withOffset(pos, direction);
					
					fakePlayer.inventory.currentItem = 0;
					
					boolean wasSuccessful = tryUseItem(is, target) || tryUseItem(is, target.down());
				}
				/*
				if (is.getItem() instanceof ItemBlock && world.isAirBlock(FacMathHelper.withOffset(pos, direction)))
				{
					world.setBlockState(FacMathHelper.withOffset(pos, direction), ((ItemBlock)is.getItem()).getBlock().getStateFromMeta(is.getItemDamage()));
					is.shrink(1);
				}
				*/
			}
		}
		else isPowered = false;
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
}