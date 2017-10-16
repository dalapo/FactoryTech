package dalapo.factech.item;

import dalapo.factech.helper.Logger;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMachinePart extends ItemBase {

	public ItemMachinePart(String name) {
		super(name, PartList.values().length);
		hasSubtypes = true;
		setMaxDamage(0);
//		setCreativeTab(CreativeTabs.TOOLS);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		if (tab == TabRegistry.FACTECH)
		{
	        for (int i = 0; i < PartList.getTotalVariants(); ++i)
	        {
//	        	if (i == PartList.NOT_A_PART.ordinal()) continue; // I hope this doesn't break anything
	            subItems.add(new ItemStack(this, 1, i));
	        }
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int acc = 0;
		int floor = 0;
		String partName = "null";
		for (int i=0; i<PartList.values().length; i++)
		{
			acc += PartList.values()[i].getNumVariants();
			if (acc > stack.getItemDamage())
			{
				partName = PartList.values()[i].getName();
				floor = PartList.values()[i].getFloor();
				break;
			}
		}
		return NameList.MODID + "." + name + ":" + partName + "-" + (stack.getItemDamage() - floor);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void initModel()
	{
		PartList[] parts = PartList.values();
		final ModelResourceLocation[] locations = new ModelResourceLocation[PartList.getTotalVariants()];
		for (int i=0, running=0; i<parts.length; i++)
		{
			for (int variant=0; variant<parts[i].getNumVariants(); variant++, running++)
			{
				locations[running] = new ModelResourceLocation(NameList.MODID + ":" + parts[i].getName() + "-" + variant, "inventory");
			}
		}
		
		ModelBakery.registerItemVariants(this, locations);
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return locations[stack.getItemDamage()];
			}
		});
	}
}