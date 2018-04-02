package dalapo.factech.item;

import java.util.List;

import javax.annotation.Nonnull;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.TabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.wrappers.BlockLiquidWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHandPump extends ItemBase
{
	public ItemHandPump(String name)
	{
		super(name);
		setMaxStackSize(1);
		setHasInformation();
		canRepair = false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void actuallyAddInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		if (!is.hasTagCompound() || is.getTagCompound().getInteger("charges") == 0) list.add("Empty");
		else
		{
			list.add(String.format("Buckets stored: %s / 8", is.getTagCompound().getInteger("charges")));
			if (is.getTagCompound().getString("liquid").startsWith("universal:"))
			{
				Fluid fluid = FluidRegistry.getFluid(is.getTagCompound().getString("liquid").substring(10));
				list.add(String.format("Liquid: %s", fluid.getLocalizedName(null)));
			}
			else
			{
				list.add(String.format("Liquid: %s", FactoryTech.BLOCK_REGISTRY.getValue(new ResourceLocation(is.getTagCompound().getString("liquid").substring(10))).getLocalizedName()));
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer ep, EnumHand hand)
	{
		super.onItemRightClick(world, ep, hand);
		
		ItemStack is = ep.getHeldItem(hand);
		if (!is.hasTagCompound())
		{
			is.setTagCompound(new NBTTagCompound());
			is.getTagCompound().setInteger("charges", 0);
		}
		NBTTagCompound nbt = is.getTagCompound();
		int charges = nbt.getInteger("charges");
		RayTraceResult raytrace = this.rayTrace(world, ep, true);
//		if (is.getItemDamage() <= 0) return new ActionResult<ItemStack>(EnumActionResult.FAIL, is);
		if (raytrace == null) return new ActionResult<ItemStack>(EnumActionResult.PASS, is);
		
		BlockPos target = raytrace.getBlockPos();
		if (world.getBlockState(target).getBlock() instanceof BlockLiquid)
		{
			BlockLiquid block = (BlockLiquid)FacBlockHelper.getBlock(world, target);
			if (charges < 8)
			{
				if (!is.getTagCompound().hasKey("liquid") || block == FactoryTech.BLOCK_REGISTRY.getValue(new ResourceLocation(nbt.getString("liquid"))))
				{
					world.setBlockToAir(target);
					nbt.setInteger("charges", charges + 1);
					nbt.setString("liquid", block.getRegistryName().toString());
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, is);
				}
			}
		}
		else if (world.getBlockState(target).getBlock() instanceof IFluidBlock)
		{
			IFluidBlock block = (IFluidBlock)FacBlockHelper.getBlock(world, target);
			if (charges < 8)
			{
				if (!is.getTagCompound().hasKey("liquid") || is.getTagCompound().getString("liquid").substring(10).equals(block.getFluid().getName()))
				{
					world.setBlockToAir(target);
					nbt.setInteger("charges", charges + 1);
					nbt.setString("liquid", "universal:" + block.getFluid().getName());
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, is);
				}
			}
		}
		else if (charges >= 1)
		{
			BlockPos toPlace = FacMathHelper.withOffset(target, raytrace.sideHit);
			String str = nbt.getString("liquid");
			boolean success = false;
			if (str.startsWith("universal:"))
			{
				Logger.info(str);
				IFluidBlock block = (IFluidBlock)FluidRegistry.getFluid(str.substring(10)).getBlock();
				FluidStack dummy = new FluidStack(FluidRegistry.getFluid(str.substring(10)), 1000);
				success = true;
				block.place(world, toPlace, dummy, true);
//				BlockLiquidWrapper wrapper = new BlockLiquidWrapper(FluidRegistry.getFluid(str.substring(10)).getBlock(), world, toPlace);
			}
			else
			{
				BlockLiquid liquidToPlace = (BlockLiquid)FactoryTech.BLOCK_REGISTRY.getValue(new ResourceLocation(nbt.getString("liquid")));
				world.setBlockState(toPlace, liquidToPlace.getFlowingBlock(liquidToPlace.getMaterial(liquidToPlace.getDefaultState())).getDefaultState());
				success = true;
			}
			if (success)
			{
				if (charges == 1) nbt.removeTag("liquid");
				nbt.setInteger("charges", charges - 1);
				world.scheduleUpdate(target, world.getBlockState(target).getBlock(), 1);
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, is);
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, is);
	}
}