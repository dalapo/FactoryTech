package dalapo.factech.helper;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import dalapo.factech.reference.NameList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class FacMiscHelper {
	
	private FacMiscHelper() {}
	
	
	public static FakePlayer getFakePlayer(World world)
	{
		if (!world.isRemote)
		{
			return FakePlayerFactory.get((WorldServer)world, new GameProfile(UUID.randomUUID(), "[FactoryTech-Fake]"));
		}
		Logger.warn("Attempted to call getFakePlayer client-side. This should not be done!");
		return null;
	}
	
	// Full credit to Reika for this function
	public static boolean hasACPower(World world, BlockPos pos, boolean[] prevTicks)
	{
		boolean cur = world.isBlockIndirectlyGettingPowered(pos) != 0;
		boolean isAC = false;
		for (boolean b : prevTicks)
		{
			if (b != cur) isAC = true;
		}
		FacArrayHelper.pushThrough(prevTicks, cur);
		return isAC;
	}
	
	public static String formatPath(String str)
	{
		return NameList.MODID + ":" + str;
	}
	
	public static void registerStateModel(Block block, int meta, String file)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(NameList.MODID + ":" + file, "inventory"));
	}
	
	public static String capitalizeFirstLetter(String str)
	{
		if (str.isEmpty()) return str;
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}