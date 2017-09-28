package dalapo.factech.tileentity.automation;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityLiftFan extends TileEntityBase implements ITickable {

	@Override
	public void update()
	{
		int strength = world.isBlockIndirectlyGettingPowered(pos);
		AxisAlignedBB hitbox = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+strength+1, pos.getZ()+1);
		for (EntityItem ei : world.getEntitiesWithinAABB(EntityItem.class, hitbox))
		{
			int dist = (int)ei.posY - pos.getY();
			ei.motionY = (strength - dist) / 30.0;
//			ei.motionX *= 0.5;
//			ei.motionZ *= 0.5;
		}
	}
}