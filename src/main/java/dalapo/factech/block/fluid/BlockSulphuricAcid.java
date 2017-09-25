package dalapo.factech.block.fluid;

import dalapo.factech.auxiliary.IFluidModel;
import dalapo.factech.auxiliary.StateMap;
import dalapo.factech.helper.Logger;
import dalapo.factech.reference.NameList;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSulphuricAcid extends BlockFluidClassic implements IFluidModel {

	public BlockSulphuricAcid(Fluid fluid, Material materialIn, String name) {
		super(fluid, materialIn);
		setRegistryName(name);
		density = 1000;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		Logger.info("Entered onEntityCollidedWithBlock");
		Logger.info(entity instanceof EntityLiving);
		if (entity instanceof EntityItem)
		{
			ItemStack is = ((EntityItem)entity).getItem();
			NBTTagCompound nbt = is.getTagCompound();
			if (nbt != null && nbt.hasKey("ench"))
			{
				nbt.removeTag("ench");
			}
		}
		else if (entity instanceof EntityLivingBase)
		{
			entity.attackEntityFrom(new DamageSource("acid").setDamageBypassesArmor(), 4);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", NameList.MODID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		Item item = Item.getItemFromBlock(this);
		StateMap map = new StateMap(NameList.MODID, "fluid", "sulphuric_flowing");
		
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, map);
		ModelLoader.setCustomStateMapper(this, map);
	}
}