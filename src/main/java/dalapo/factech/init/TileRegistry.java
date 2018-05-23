package dalapo.factech.init;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.FactoryTech;
import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.Logger;
import dalapo.factech.render.tesr.*;
import dalapo.factech.tileentity.*;
import dalapo.factech.tileentity.automation.*;
import dalapo.factech.tileentity.specialized.*;

public class TileRegistry {
	public static void init()
	{
		Logger.info("Entered TileRegistry.init");
		GameRegistry.registerTileEntity(TileEntitySaw.class, "saw");
		GameRegistry.registerTileEntity(TileEntityMetalCutter.class, "metalcutter");
		GameRegistry.registerTileEntity(TileEntityPotionMixer.class, "potionmixer");
		GameRegistry.registerTileEntity(TileEntityDisruptor.class, "disruptor");
		GameRegistry.registerTileEntity(TileEntityStackMover.class, "stackmover");
		GameRegistry.registerTileEntity(TileEntityFilterMover.class, "filtermover");
		GameRegistry.registerTileEntity(TileEntityBulkMover.class, "bulkmover");
		GameRegistry.registerTileEntity(TileEntityAutoPuller.class, "autopuller");
		GameRegistry.registerTileEntity(TileEntityHTFurnace.class, "htfurnace");
		GameRegistry.registerTileEntity(TileEntityPropaneFurnace.class, "propfurnace");
		GameRegistry.registerTileEntity(TileEntityOreDrill.class, "oredrill");
		GameRegistry.registerTileEntity(TileEntityAutoCrafter.class, "autocrafter");
		GameRegistry.registerTileEntity(TileEntityPipe.class, "pipe");
		GameRegistry.registerTileEntity(TileEntityCrucible.class, "crucible");
		GameRegistry.registerTileEntity(TileEntityGrindstone.class, "grindstone");
		GameRegistry.registerTileEntity(TileEntityItemPusher.class, "itempusher");
		GameRegistry.registerTileEntity(TileEntityItemRedis.class, "itemredis");
		GameRegistry.registerTileEntity(TileEntityFluidPuller.class, "fluidpuller");
		GameRegistry.registerTileEntity(TileEntityCircuitScribe.class, "circuitscribe");
		GameRegistry.registerTileEntity(TileEntityCentrifuge.class, "centrifuge");
		GameRegistry.registerTileEntity(TileEntityFluidDrill.class, "fluiddrill");
		GameRegistry.registerTileEntity(TileEntityAgitator.class, "agitator");
		GameRegistry.registerTileEntity(TileEntitySluice.class, "sluice");
		GameRegistry.registerTileEntity(TileEntityAutoMiner.class, "autominer");
		GameRegistry.registerTileEntity(TileEntityElectroplater.class, "electroplater");
		GameRegistry.registerTileEntity(TileEntityMagnet.class, "magnet");
		GameRegistry.registerTileEntity(TileEntityStabilizer.class, "stabilizer");
		GameRegistry.registerTileEntity(TileEntityLiftFan.class, "elevator");
		GameRegistry.registerTileEntity(TileEntityMagnetizer.class, "magnetizer");
		GameRegistry.registerTileEntity(TileEntityCoreCharger.class, "charger");
		GameRegistry.registerTileEntity(TileEntityCompressionChamber.class, "compressor");
		GameRegistry.registerTileEntity(TileEntitySpawner.class, "spawner");
		GameRegistry.registerTileEntity(TileEntityDisassembler.class, "disassembler");
		GameRegistry.registerTileEntity(TileEntityDecoCoil.class, "decocoil");
		GameRegistry.registerTileEntity(TileEntityWaterCollector.class, "watercollector");
		GameRegistry.registerTileEntity(TileEntityCrate.class, "crate");
		GameRegistry.registerTileEntity(TileEntityBufferCrate.class, "buffercrate");
		GameRegistry.registerTileEntity(TileEntityEnergizer.class, "energizer");
		GameRegistry.registerTileEntity(TileEntityRefrigerator.class, "fridge");
		GameRegistry.registerTileEntity(TileEntityWoodcutter.class, "woodcutter");
		GameRegistry.registerTileEntity(TileEntityIonDisperser.class, "iondisperser");
		GameRegistry.registerTileEntity(TileEntityTeslaCoil.class, "teslacoil");
		GameRegistry.registerTileEntity(TileEntityMechArm.class, "mecharm");
		GameRegistry.registerTileEntity(TileEntitySequencePlacer.class, "sequenceplacer");
		GameRegistry.registerTileEntity(TileEntityTank.class, "tank");
		GameRegistry.registerTileEntity(TileEntityTemperer.class, "temperer");
		GameRegistry.registerTileEntity(TileEntityBlockBreaker.class, "blockbreaker");
		GameRegistry.registerTileEntity(TileEntityDeepDrill.class, "deepdrill");
		GameRegistry.registerTileEntity(TileEntityPlanter.class, "planter");
		GameRegistry.registerTileEntity(TileEntityInventorySensor.class, "inventorysensor");
		GameRegistry.registerTileEntity(TileEntityItemInterceptor.class, "interceptor");
		GameRegistry.registerTileEntity(TileEntityAerolyzer.class, "aerolyzer");
		GameRegistry.registerTileEntity(TileEntityMagnetCentrifuge.class, "magcent");
		GameRegistry.registerTileEntity(TileEntityPlaneShifter.class, "planeshifter");
		GameRegistry.registerTileEntity(TileEntityAnimatedModel.class, "animatedmodel");
		GameRegistry.registerTileEntity(TileEntityElevator.class, "realelevator");
	}
	
	@SideOnly(Side.CLIENT)
	public static void initTESRs() {
		if (FacTechConfigManager.doTesrs)
		{
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySaw.class, new TesrSaw(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOreDrill.class, new TesrOreDrill(false));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoCoil.class, new TesrDecoCoil(false));
	//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAutoMiner.class, new TesrMiner(false));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCentrifuge.class, new TesrCentrifuge(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCoreCharger.class, new TesrCharger(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMetalCutter.class, new TesrMetalCutter(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCircuitScribe.class, new TesrCircuitScribe(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTemperer.class, new TesrTemperer(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagnetizer.class, new TesrMagnetizer(true));
//			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisassembler.class, new TesrDisassembler(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStabilizer.class, new TesrStabilizer(false));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockBreaker.class, new TesrBlockBreaker(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWoodcutter.class, new TesrWoodcutter(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagnetCentrifuge.class, new TesrMagCentrifuge(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimatedModel.class, new TesrAnimatedModel(true));
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityElevator.class, new TesrElevator());
		}
	}
}