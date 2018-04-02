package dalapo.factech.item;

import dalapo.factech.block.BlockRSNotifier;
import dalapo.factech.helper.FacChatHelper;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRedWatcher extends ItemBase
{
	public ItemRedWatcher(String name)
	{
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		super.initModel();
		final ModelResourceLocation active = new ModelResourceLocation(getRegistryName() + "_on", "inventory");
		final ModelResourceLocation inactive = new ModelResourceLocation(getRegistryName() + "_off", "inventory");
		
		ModelBakery.registerItemVariants(this, active, inactive);
		
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack is)
			{
				if (is.getItemDamage() == 1) return active;
				else return inactive;
			}
		});
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity ep, int i, boolean b)
	{
		super.onUpdate(itemstack, world, ep, i, b);
		if (!itemstack.hasTagCompound()) return;
		// 1.7.10 legacy code...
		int x = itemstack.getTagCompound().getInteger("bound_x");
		int y = itemstack.getTagCompound().getInteger("bound_y");
		int z = itemstack.getTagCompound().getInteger("bound_z");
		if (world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockRSNotifier && world.isBlockPowered(new BlockPos(x, y, z)))
		{
			itemstack.setItemDamage(1);
		}
		else itemstack.setItemDamage(0);
		
		if (!(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockRSNotifier))
		{
			itemstack.setTagCompound(null);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer ep, World world, BlockPos pos, EnumHand hand, EnumFacing side, float pX, float pY, float pZ)
	{
		if (!(world.getBlockState(pos).getBlock() instanceof BlockRSNotifier)) return EnumActionResult.PASS;
		ItemStack itemstack = ep.getHeldItem(hand);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("bound_x", pos.getX());
		nbt.setInteger("bound_y", pos.getY());
		nbt.setInteger("bound_z", pos.getZ());
		itemstack.setTagCompound(nbt);
		FacChatHelper.sendCoords("Linked to ", pos);
		return EnumActionResult.SUCCESS;
	}
}