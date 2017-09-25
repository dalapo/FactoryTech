package dalapo.factech.item;

import dalapo.factech.init.TabRegistry;
import dalapo.factech.reference.NameList;
import dalapo.factech.reference.PartList;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBase extends Item {
	protected final String name;
	private final int subtypes;
	
	public ItemBase(String name, int subtypes)
	{
		super();
//		setCreativeTab(TabRegistry.FACTECH);
		this.name = name;
		this.subtypes = subtypes > 1 ? subtypes : 1;
		if (subtypes > 1)
		{
			hasSubtypes = true;
			setMaxDamage(0);
		}
		setUnlocalizedName(NameList.MODID + "." + name);
		setRegistryName(name);
//		GameRegistry.register(this);
	}
	
	public ItemBase(String name)
	{
		this(name, 1);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		if (tab == TabRegistry.FACTECH)
		{
			for (int i=0; i<subtypes; i++)
			{
				subItems.add(new ItemStack(this, 1, i));
			}
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (hasSubtypes) return NameList.MODID + "." + name + ":" + stack.getItemDamage();
		else return NameList.MODID + "." + name;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		if (hasSubtypes)
		{
			final ModelResourceLocation[] locations = new ModelResourceLocation[subtypes];
			for (int i=0; i<subtypes; i++)
			{
				locations[i] = new ModelResourceLocation(getRegistryName() + "_" + i, "inventory");
			}
			ModelBakery.registerItemVariants(this, locations);
			ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
				@Override
				public ModelResourceLocation getModelLocation(ItemStack stack) {
					return locations[stack.getItemDamage()];
				}
			});
		}
		else
		{
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
	}
}