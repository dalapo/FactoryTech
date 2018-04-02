package dalapo.factech.item;

import dalapo.factech.block.IBlockSubtypes;
import dalapo.factech.reference.NameList;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMod extends ItemBlock {

	public ItemBlockMod(Block block) {
		super(block);
		if (!(block instanceof IBlockSubtypes)) throw new IllegalArgumentException();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	public int getMetadata(int dmg)
	{
		return dmg;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return ((IBlockSubtypes)block).getName(stack) + ":" + stack.getItemDamage();
	}
}