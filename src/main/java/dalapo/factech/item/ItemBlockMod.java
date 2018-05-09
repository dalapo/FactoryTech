package dalapo.factech.item;

import java.util.List;

import dalapo.factech.block.IBlockSubtypes;
import dalapo.factech.block.BlockBase;
import dalapo.factech.reference.MachineInfoList;
import dalapo.factech.reference.NameList;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockMod extends ItemBlock {

	public ItemBlockMod(BlockBase block) {
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
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, World world, List<String> list, ITooltipFlag flags)
	{
		super.addInformation(is, world, list, flags);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return ((IBlockSubtypes)block).getName(stack) + ":" + stack.getItemDamage();
	}
}