package dalapo.factech.block;

import dalapo.factech.auxiliary.EnumBrickType;
import dalapo.factech.auxiliary.EnumOreType;
import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.StateList;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
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

public class BlockBricks extends BlockBase implements IBlockSubtypes {

	public BlockBricks(Material materialIn, String name)
	{
		super(materialIn, name);
		setDefaultState(blockState.getBaseState().withProperty(StateList.bricktype, EnumBrickType.SAND));
		this.blockHardness = 4F;
		this.blockResistance = 5F;
		this.setHarvestLevel("pickaxe", 0);
	}

	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		ResourceLocation[] arr = new ResourceLocation[EnumBrickType.values().length];
		for (int i=0; i<arr.length; i++)
		{
			arr[i] = new ResourceLocation(NameList.MODID + ":bricks_" + EnumBrickType.values()[i].getName());
		}
		ModelBakery.registerItemVariants(Item.getItemFromBlock(this), arr);
		
		for (int i=0; i<arr.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(NameList.MODID + ":bricks", EnumBrickType.values()[i].getName()));
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
		return new BlockStateContainer(this, StateList.bricktype);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(StateList.bricktype, EnumBrickType.getType(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(StateList.bricktype).getMeta();
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(StateList.bricktype).getMeta();
	}
	
	@Override
	public String getName(ItemStack is) {
		return this.getUnlocalizedName();
	}

}
