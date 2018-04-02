package dalapo.factech.config;

import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import dalapo.factech.auxiliary.MachinePart;
import dalapo.factech.tileentity.specialized.*;

public enum MachineDefaults {
	TileEntityAerolyzer(TileEntityAerolyzer.class,
					   new MachinePart(PartList.MOTOR, 0.5F, 1.35F, 1.0F, 6),
					   new MachinePart(PartList.CIRCUIT_1, 0.2F, 1.5F, 0.8F, 6)),
	
	TileEntityAgitator(TileEntityAgitator.class,
					   new MachinePart(PartList.MIXER, 0.2F, 1.2F, 0, 15),
					   new MachinePart(PartList.MOTOR, 0.35F, 1.3F, 0.95F, 6),
					   new MachinePart(PartList.CIRCUIT_3, 0.67F, 1.15F, 0.6F, 7)),
	
	TileEntityAutoCrafter(TileEntityAutoCrafter.class,
					   new MachinePart(PartList.GEAR, 0F, 0F, 1F, 1)),
	
	TileEntityAutoMiner(TileEntityAutoMiner.class,
						new MachinePart(PartList.DRILL, 0.1F, 1.0F, 0.5F, 14),
						new MachinePart(PartList.CIRCUIT_0, 0.1F, 1.0F, 0.6F, 16),
						new MachinePart(PartList.MOTOR, 0.2F, 1.1F, 1F, 16)),
	
	TileEntityCentrifuge(TileEntityCentrifuge.class,
						 new MachinePart(PartList.MOTOR, 0.15F, 1.07F, 0.9F, 7),
		 				 new MachinePart(PartList.GEAR, 0.25F, 1.1F, 0.7F, 9),
		 				 new MachinePart(PartList.SHAFT, 0.05F, 1.3F, 0.75F, 16)),
	
	TileEntityCircuitScribe(TileEntityCircuitScribe.class,
							new MachinePart(PartList.HEATELEM, 0.15F, 1.2F, 0.55F, 12),
		 					new MachinePart(PartList.BLADE, 0.2F, 1.05F, 0.2F, 5),
		 					new MachinePart(PartList.MOTOR, 0.1F, 1.2F, 0.875F, 8)),
	
	TileEntityCompressionChamber(TileEntityCompressionChamber.class,
								 new MachinePart(PartList.PISTON, 0.15F, 1.2F, 0.66F, 8),
		 						 new MachinePart(PartList.MOTOR, 0.2F, 1.05F, 0.875F, 8)),
	
	TileEntityCoreCharger(TileEntityCoreCharger.class,
						  new MachinePart(PartList.BATTERY, 0.05F, 1.2F, 0.8F, 10),
						  new MachinePart(PartList.CIRCUIT_0, 0.02F, 1.05F, 0.75F, 17),
		 				  new MachinePart(PartList.CIRCUIT_1, 0.02F, 1.05F, 0.75F, 17),
		 				  new MachinePart(PartList.MAGNET, 0.05F, 1.05F, 0.75F, 15)),
	TileEntityCrucible(TileEntityCrucible.class,
					   new MachinePart(PartList.HEATELEM, 0.1F, 1.15F, 0.56F, 6),
					   new MachinePart(PartList.MIXER, 0.15F, 1.15F, 16)),
	
	TileEntityDeepDrill(TileEntityDeepDrill.class,
						new MachinePart(PartList.CORE, 0.4F, 1.2F, 0.8F, 12),
	 					new MachinePart(PartList.CIRCUIT_0, 0.3F, 1.3F, 0.6F, 8),
	 					new MachinePart(PartList.DRILL, 0.5F, 1.5F, 0.7F, 6),
	 					new MachinePart(PartList.LENS, 0.5F, 1.1F, 0.0F, 16)),
	
	TileEntityDisassembler(TileEntityDisassembler.class,
						   new MachinePart(PartList.SAW, 0.4F, 1.1F, 0.5F, 6),
		 				   new MachinePart(PartList.BATTERY, 0.35F, 1.01F, 0.9F, 5),
		 				   new MachinePart(PartList.CIRCUIT_2, 0.4F, 1.2F, 0.8F, 5)),
	
	TileEntityDisruptor(TileEntityDisruptor.class,
						new MachinePart(PartList.CORE, 0.1F, 1.2F, 0.8F, 6),
		 				new MachinePart(PartList.MAGNET, new ItemStack(Items.IRON_INGOT), 0.2F, 1.25F, 1F, 8),
		 				new MachinePart(PartList.CIRCUIT_3, 0.2F, 1.25F, 0.6F, 6),
		 				new MachinePart(PartList.PISTON, 0.15F, 1.3F, 0.8F, 6)),
	
	TileEntityElectroplater(TileEntityElectroplater.class,
							new MachinePart(PartList.BATTERY, 0.67F, 1.5F, 0.8F, 8),
		 					new MachinePart(PartList.WIRE, 0.4F, 1.2F, 1F, 6),
		 					new MachinePart(PartList.MIXER, 0.1F, 1.1F, 16)),
	
	TileEntityFluidDrill(TileEntityFluidDrill.class,
						 new MachinePart(PartList.DRILL, 0.2F, 1.2F, 0.4F, 8),
		 				 new MachinePart(PartList.CIRCUIT_1, 0.1F, 1.4F, 0.6F, 6),
		 				 new MachinePart(PartList.MOTOR, 0.2F, 1.1F, 0.95F, 6)),
	
	TileEntityGrindstone(TileEntityGrindstone.class, new MachinePart(PartList.GEAR, 0.1F, 1.1F, 0.85F, 7)),
	
	TileEntityHTFurnace(TileEntityHTFurnace.class,
						new MachinePart(PartList.HEATELEM, 0.5F, 1.3F, 1.0F, 10),
		 				new MachinePart(PartList.WIRE, 0.5F, 1.0F, 0.7F, 5)),
	
	TileEntityIonDisperser(TileEntityIonDisperser.class,
						   new MachinePart(PartList.CORE, 0.05F, 1.1F, 0.8F, 0),
		 				   new MachinePart(PartList.CIRCUIT_2, 0.1F, 1.1F, 0.6F, 4),
		 				   new MachinePart(PartList.CIRCUIT_3, 0.1F, 1.1F, 0.6F, 4),
		 				   new MachinePart(PartList.BATTERY, 0.15F, 1.1F, 0.9F, 10)),
	
	TileEntityMagnetizer(TileEntityMagnetizer.class,
						 new MachinePart(PartList.MOTOR, 0.1F, 1.05F, 0.6F, 8),
						 new MachinePart(PartList.WIRE, 0.4F, 1.25F, 0.65F, 5)),
	
	TileEntityMagnetCentrifuge(TileEntityMagnetCentrifuge.class,
						 new MachinePart(PartList.MAGNET, 0.25F, 1.2F, 1, 7),
						 new MachinePart(PartList.MOTOR, 0.33F, 1.3F, 0.875F, 8),
						 new MachinePart(PartList.WIRE, 0.2F, 1.5F, 0.8F, 8)),
	
	TileEntityMetalCutter(TileEntityMetalCutter.class,
						  new MachinePart(PartList.BLADE, 0.1F, 1.1F, 0.5F, 8),
		 				  new MachinePart(PartList.GEAR, 0.2F, 1.2F, 0.7F, 6)),
	
	TileEntityOreDrill(TileEntityOreDrill.class,
						  new MachinePart(PartList.MOTOR, 0.4F, 1.1F, 0.85F, 8),
						  new MachinePart(PartList.DRILL, 0.1F, 1.2F, 0.5F, 4)),
	
	TileEntityPlanter(TileEntityPlanter.class,
					      new MachinePart(PartList.PISTON, 0.1F, 1.15F, 0.8F, 5),
					      new MachinePart(PartList.CIRCUIT_2, 0.1F, 1.2F, 0.85F, 8)),
	
	TileEntityPotionMixer(TileEntityPotionMixer.class,
						  new MachinePart(PartList.MIXER, 0.1F, 1.05F, 6),
						  new MachinePart(PartList.GEAR, 0.05F, 1.1F, 0.8F, 0)),
	
	TileEntityRefrigerator(TileEntityRefrigerator.class,
						   new MachinePart(PartList.PISTON, 0.2F, 1.15F, 0.7F, 7),
						   new MachinePart(PartList.MOTOR, 0.3F, 1.1F, 0.9F, 7),
						   new MachinePart(PartList.CIRCUIT_2, 0.2F, 1.3F, 0.6F, 8)),
	
	TileEntitySaw(TileEntitySaw.class,
				  new MachinePart(PartList.SAW, 0.15F, 1.1F, 0.7F, 10),
				  new MachinePart(PartList.GEAR, 0.1F, 1.05F, 0.8F, 9)),
	
	TileEntitySluice(TileEntitySluice.class, new MachinePart(PartList.MESH, new ItemStack(Items.STICK, 3), 0.05F, 1.2F, 0.75F, 12)),
	
	TileEntitySpawner(TileEntitySpawner.class,
					  new MachinePart(PartList.CORE, 0.1F, 1.15F, 0.8F, 6),
					  new MachinePart(PartList.CIRCUIT_0, 0.15F, 1.1F, 0.5F, 5),
					  new MachinePart(PartList.BATTERY, 0.075F, 1.1F, 0.8F, 0),
					  new MachinePart(PartList.MOTOR, 0.2F, 1.2F, 0.8F, 4)),
	
	TileEntityTemperer(TileEntityTemperer.class,
					   new MachinePart(PartList.HEATELEM, 0.5F, 1.2F, 0.55F, 10),
					   new MachinePart(PartList.CIRCUIT_2, 0.2F, 1.1F, 0.75F, 8),
					   new MachinePart(PartList.LENS, 0.5F, 1.0F, 0, 8)),
	
	TileEntityTeslaCoil(TileEntityTeslaCoil.class,
						new MachinePart(PartList.BATTERY, 0.3F, 1.05F, 0.9F, 4),
						new MachinePart(PartList.WIRE, 0.2F, 1.1F, 0.6F, 6)),
	
	TileEntityWoodcutter(TileEntityWoodcutter.class,
						 new MachinePart(PartList.SAW, 0.1F, 1.05F, 0.6F, 6),
						 new MachinePart(PartList.MOTOR, 0.4F, 1.15F, 0.95F, 8));
	
	private MachineDefaults(Class<? extends TileEntityMachine> c, MachinePart... parts)
	{
		clazz = c;
		partsNeeded = parts;
	}
	
	public static MachineDefaults getFromString(String str)
	{
		for (MachineDefaults mds : values())
		{
			if (mds.name().equalsIgnoreCase(str))
			{
				return mds;
			}
		}
		return null;
	}
	
	public final Class<? extends TileEntityMachine> clazz;
	public final MachinePart[] partsNeeded;
}
