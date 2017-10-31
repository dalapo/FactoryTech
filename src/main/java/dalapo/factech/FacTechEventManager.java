package dalapo.factech;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.PotionLockdown;
import dalapo.factech.block.IBlockSubtypes;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.DictRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.RecipeRegistry;
import dalapo.factech.item.ItemBlockMod;
import dalapo.factech.tileentity.specialized.TileEntityDisassembler;

@Mod.EventBusSubscriber
public class FacTechEventManager {
	private FacTechEventManager() {}
	
	public static FacTechEventManager instance = new FacTechEventManager();
	
	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		Logger.info("Entered registerBlocks");
		BlockRegistry.init();
		for (Block b : BlockRegistry.blocks)
		{
			event.getRegistry().register(b);
		}
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		ItemRegistry.init();
		for (Block b : BlockRegistry.blocks)
		{
			if (b instanceof IBlockSubtypes)
			{
				event.getRegistry().register(new ItemBlockMod(b).setRegistryName(b.getRegistryName()));
			}
			else
			{
				event.getRegistry().register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
			}
		}
		for (Item i : ItemRegistry.items)
		{
			event.getRegistry().register(i);
		}
		DictRegistry.registerOreDictEntries();
	}
	
	@SubscribeEvent
	public void registerPotions(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().register(PotionLockdown.INSTANCE);
	}
	
	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		RecipeRegistry.init();
	}
	
	@SubscribeEvent
	public void cancelDisassemblyDrops(LivingDropsEvent evt)
	{
		if (evt.getSource().damageType.equals("machine") && MachineRecipes.DISASSEMBLER.containsKey(evt.getEntity().getClass()))
		{
			evt.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void blockTeleport(EnderTeleportEvent evt)
	{
		if (evt.getEntityLiving() instanceof EntityEnderman)
		{
			BlockPos enderPos = evt.getEntity().getPosition();
			if (evt.getEntityLiving().world.getTileEntity(FacMathHelper.withOffset(enderPos, EnumFacing.DOWN)) instanceof TileEntityDisassembler ||
					evt.getEntityLiving().getActivePotionEffect(PotionLockdown.INSTANCE) != null)
			{
				evt.setCanceled(true);
			}
		}
	}
}
