package dalapo.factech.block;

import dalapo.factech.auxiliary.EnumOreBlockType;
import dalapo.factech.auxiliary.EnumOreType;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOreStorage extends BlockBase implements IBlockSubtypes {

	public BlockOreStorage(String name)
	{
		super(Material.IRON, name);
		setDefaultState(blockState.getBaseState().withProperty(StateList.oreblocktype, EnumOreBlockType.COPPER));
		setHardness(5F);
		setResistance(5F);
		setHarvestLevel("pickaxe", 1);
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ResourceLocation[] arr = new ResourceLocation[EnumOreBlockType.values().length];
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = new ResourceLocation(NameList.MODID + ":oreblock_" + EnumOreBlockType.values()[i].getName());
		}
		ModelBakery.registerItemVariants(Item.getItemFromBlock(this), arr);
		
		for (int i=0; i<arr.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(NameList.MODID + ":oreblock", EnumOreBlockType.values()[i].getName()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> stacks)
	{
		if (tab == TabRegistry.FACTECH)
		{
			for (int i=0; i<4; i++)
			{
				stacks.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, StateList.oreblocktype);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(StateList.oreblocktype, EnumOreBlockType.getType(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.oreblocktype).getMeta();
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(StateList.oreblocktype).getMeta();
	}
	
	@Override
	public String getName(ItemStack is) {
		return this.getUnlocalizedName();
	}

}
