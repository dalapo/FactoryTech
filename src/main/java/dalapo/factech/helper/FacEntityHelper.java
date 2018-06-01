package dalapo.factech.helper;

import net.minecraft.entity.Entity;

public class FacEntityHelper
{
	private FacEntityHelper() {}
	
	public static void stopEntity(Entity entity)
	{
		entity.motionX = 0;
		entity.motionY = 0;
		entity.motionZ = 0;
	}
}