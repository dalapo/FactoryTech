package dalapo.factech.block;

import javax.annotation.Nonnull;

import dalapo.factech.auxiliary.EnumOreType;
import dalapo.factech.auxiliary.StateMap;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends BlockBase implements IBlockSubtypes {

	public BlockOre(String name) {
		super(Material.ROCK, name);
		setDefaultState(blockState.getBaseState().withProperty(StateList.oretype, EnumOreType.COPPER));
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ModelBakery.registerItemVariants(Item.getItemFromBlock(this), new ResourceLocation("factorytech:ore_copper"), new ResourceLocation("factorytech:ore_nickel"));

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation("factorytech:ore", "copper"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation("factorytech:ore", "nickel"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> stacks)
	{
		if (tab == TabRegistry.FACTECH)
		{
		stacks.add(new ItemStack(this, 1, 0));
		stacks.add(new ItemStack(this, 1, 1));
		}
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, StateList.oretype);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(StateList.oretype, EnumOreType.getType(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.oretype).getMeta();
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(StateList.oretype).getMeta();
	}

	@Override
	public String getName(ItemStack is) {
		return this.getUnlocalizedName();
	}
}