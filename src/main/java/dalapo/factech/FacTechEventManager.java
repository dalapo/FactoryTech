package dalapo.factech;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.auxiliary.PotionLockdown;
import dalapo.factech.block.BlockBase;
import dalapo.factech.block.IBlockSubtypes;
import dalapo.factech.entity.EntityHoverScooter;
import dalapo.factech.entity.EntityPressureGunShot;
import dalapo.factech.helper.FacBlockHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.DictRegistry;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.init.RecipeRegistry;
import dalapo.factech.item.ItemBlockMod;
import dalapo.factech.reference.NameList;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.automation.TileEntityTank;
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
	public void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		event.getRegistry().register(new EntityEntry(EntityPressureGunShot.class, "PressureGunShot").setRegistryName("PressureGunShot"));
		event.getRegistry().register(new EntityEntry(EntityHoverScooter.class, "hoverscooter").setRegistryName("hoverscooter"));
		int id = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(NameList.MODID, "pressureshot"), EntityPressureGunShot.class, "pressureshot", id++, FactoryTech.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(NameList.MODID, "hoverscooter"), EntityHoverScooter.class, "hoverscooter", id++, FactoryTech.instance, 64, 300, true);
	}
	
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		ItemRegistry.init();
		for (BlockBase b : BlockRegistry.blocks)
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
	
	@SubscribeEvent
	public void noShootingForYou(LivingEntityUseItemEvent.Start e)
	{
		if (!(e.getEntityLiving() instanceof EntityPlayer) && e.getEntityLiving().getActivePotionEffect(PotionLockdown.INSTANCE) != null && e.getItem().getItem() == Items.BOW)
		{
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void syncTankFill(FluidEvent e)
	{
		TileEntity te = e.getWorld().getTileEntity(e.getPos());
		// Don't mess with other mods' stuff
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();
		if (BlockRegistry.blocks.contains(b))
		{
			FacBlockHelper.updateBlock(e.getWorld(), e.getPos());
			if (te instanceof TileEntityMachine)
			{
				((TileEntityMachine)te).getHasWork();
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void updateLanguage(GuiScreenEvent.ActionPerformedEvent e)
	{
		if (e.getGui() instanceof GuiLanguage)
		{
			((ClientProxy)FactoryTech.proxy).language = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
			((ClientProxy)FactoryTech.proxy).initHandbookPages();
		}
	}
}
