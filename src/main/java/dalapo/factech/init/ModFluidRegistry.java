package dalapo.factech.init;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.auxiliary.IFluidModel;
import dalapo.factech.block.fluid.BlockSulphuricAcid;
import dalapo.factech.block.fluid.FluidBase;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModFluidRegistry {
	public static List<FluidBase> fluids = new ArrayList<FluidBase>();

	public static FluidBase h2so4;
	public static FluidBase propane;
	public static FluidBase sulphur;
	public static FluidBase energite;
	public static FluidBase glowstone;
	
	public static BlockSulphuricAcid h2so4Block;
	
	public static void init()
	{
		fluids.add(h2so4 = (FluidBase)new FluidBase("h2so4").setDensity(1000));
		fluids.add(propane = (FluidBase)new FluidBase("propane").setDensity(20));
		fluids.add(sulphur = (FluidBase)new FluidBase("sulphur").setDensity(1500).setTemperature(400));
		fluids.add(energite = (FluidBase)new FluidBase("energite").setDensity(100).setTemperature(350));
		fluids.add(glowstone = (FluidBase)new FluidBase("glowstone").setDensity(0).setTemperature(500));
		
		for (FluidBase f : fluids)
		{
			FluidRegistry.registerFluid(f);
			FluidRegistry.addBucketForFluid(f);
		}
		
		h2so4Block = new BlockSulphuricAcid(h2so4, new MaterialLiquid(MapColor.GRAY), "h2so4");
//		GameRegistry.register(h2so4Block);
		h2so4.setBlock(h2so4Block);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initTextures(TextureStitchEvent.Pre evt)
	{
		TextureMap map = evt.getMap();
		
		registerFluidTexture(map, h2so4);
		registerFluidTexture(map, propane);
		registerFluidTexture(map, sulphur);
		registerFluidTexture(map, glowstone);
		registerFluidTexture(map, energite);
		
		h2so4Block.registerModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerFluidTexture(TextureMap map, FluidBase fluid)
	{
		map.registerSprite(fluid.getStill());
		map.registerSprite(fluid.getFlowing());
	}
}