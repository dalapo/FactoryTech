package dalapo.factech.item;

import dalapo.factech.init.TabRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemElementSword extends ItemSword implements ItemModelProvider
{
	public ItemElementSword(ToolMaterial material, String name)
	{
		super(material);
		setRegistryName(name);
		setUnlocalizedName("factorytech:" + name);
		setCreativeTab(TabRegistry.FACTECH);
		canRepair = false;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity ep, int i, boolean b)
	{
		super.onUpdate(stack, world, ep, i, b);
		if (ep.isInWater() && b)
		{
			ep.attackEntityFrom(new DamageSource("tesla"), 2);
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack is, EntityLivingBase target, EntityLivingBase attacker)
	{
		super.hitEntity(is, target, attacker);
		target.setFire(6);
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel() 
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}