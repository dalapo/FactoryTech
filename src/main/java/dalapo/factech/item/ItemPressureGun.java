package dalapo.factech.item;

import java.util.List;

import dalapo.factech.entity.EntityPressureGunShot;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ItemRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// Compressed Air: Knockback
// Water: Knockback + Damage
// H2SO4: High damage
// Propane: Damage + Fire

public class ItemPressureGun extends ItemBase
{

	public ItemPressureGun(String name)
	{
		super(name);
		setMaxDamage(301); // 300 ticks = 15 seconds per tank
		setMaxStackSize(1);
		canRepair = false;
	}
	
	private int getTankID(ItemStack is)
	{
		if (!is.hasTagCompound())
		{
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("tank", -1);
			is.setTagCompound(nbt);
		}
		return is.getTagCompound().getInteger("tank");
	}
	
	@Override
	protected void actuallyAddInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		int tank = getTankID(is);
		String str = "Active fluid: ";
		switch (tank)
		{
		case -1:
			str += "None";
			break;
		case 0:
			str += "Air";
			break;
		case 1:
			str += "Water";
			break;
		case 2:
			str += "Propane";
			break;
		case 3:
			str += "H2SO4";
			break;
		case 4:
			str += "Sulphur";
			break;
		case 5:
			str += "Glowstone";
			break;
		case 6:
			str += "Energite";
			break;
		}
		list.add(str);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 20;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityLivingBase ep, int count)
	{
		super.onPlayerStoppedUsing(is, world, ep, count);
	}
	
	@Override
	public void onUsingTick(ItemStack is, EntityLivingBase ep, int count)
	{
		super.onUsingTick(is, ep, count);
		
		if (getTankID(is) == -1) return;
		if (is.getItemDamage() < 300)
		{
			int tank = getTankID(ep.getHeldItemMainhand());
			int shots = 1;
			if (tank == 0 || tank == 2) shots = 5;
			is.setItemDamage(is.getItemDamage()+1);
			for (int i=0; i<shots; i++)
			{
				EntityPressureGunShot shot = new EntityPressureGunShot(ep.world, ep, tank, isFreshestWeapon(is));
				Vec3d lookDir = ep.getLookVec();
				double spread = shot.getProjType().spread;
				Vec3d motionVec = lookDir.addVector(ep.getRNG().nextGaussian()*spread, ep.getRNG().nextGaussian()*spread, ep.getRNG().nextGaussian()*spread);
				shot.motionX = motionVec.x;
				shot.motionY = motionVec.y;
				shot.motionZ = motionVec.z;
				ep.world.spawnEntity(shot);
			}
		}
		if (is.getItemDamage() == 300)
		{
			is.setItemDamage(0);
			ItemStack tank = new ItemStack(ItemRegistry.tank, 1, 0);
			ep.world.spawnEntity(new EntityItem(ep.world, ep.posX, ep.posY, ep.posZ, tank));
			is.getTagCompound().setInteger("tank", -1);
		}
	}
	
	private boolean isFreshestWeapon(ItemStack is)
	{
		return (is.getDisplayName().equals("Splattershot"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer ep, EnumHand hand)
	{
		ItemStack is = ep.getHeldItem(hand);
		
		int tankNum = getTankID(is);
//		Logger.info(tankNum);
		if (tankNum == -1)
		{
			super.onItemRightClick(world, ep, hand);
			ItemStack tank = FacStackHelper.findStack(ep.inventory, itemstack -> (itemstack.getItemDamage() != 0 && itemstack.getItem() == ItemRegistry.tank), !ep.isCreative());
			if (tank.isEmpty())
			{
				return ActionResult.newResult(EnumActionResult.FAIL, is);
			}
			else
			{
				tankNum = tank.getItemDamage() - 1;
				is.getTagCompound().setInteger("tank", tankNum);
				is.setItemDamage(0);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, is);
		}
		else
		{
			ep.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.PASS, is);
		}
	}
}
