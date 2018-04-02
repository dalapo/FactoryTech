package dalapo.factech.tileentity.specialized;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import dalapo.factech.FactoryTech;
import dalapo.factech.reference.AABBList;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityAreaMachine;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityIonDisperser extends TileEntityAreaMachine {

	private static final Potion[] EFFECTS = new Potion[]
	{
		MobEffects.SPEED,
		MobEffects.HASTE,
		MobEffects.REGENERATION,
		MobEffects.JUMP_BOOST,
		MobEffects.STRENGTH
	};
	
	public TileEntityIonDisperser() {
		super("iondisperser", 0, 4, 0, RelativeSide.ANY, 8);
		setDisplayName("Negative Ion Disperser");
	}

	@Override
	protected boolean performAction()
	{
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AABBList.getCube(pos, getAdjustedRange()));
		
		for (EntityLivingBase creature : entities)
		{
			for (Potion p : EFFECTS)
			{
				creature.addPotionEffect(new PotionEffect(p, 200));
			}
		}
		
		return true;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 200;
	}

}