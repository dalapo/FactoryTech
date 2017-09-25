package dalapo.factech.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import dalapo.factech.world.OreGenerator;

public class WorldGenRegistry
{
	private WorldGenRegistry() {}
	
	public static void init()
	{
		GameRegistry.registerWorldGenerator(new OreGenerator(), 0);
	}
}
