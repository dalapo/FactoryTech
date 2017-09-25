package dalapo.factech.auxiliary;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

// L1: Creepers cannot explode, endermen cannot teleport
// L2: L1 plus skeletons cannot shoot
public class PotionLockdown extends Potion {

	public static final PotionLockdown INSTANCE = new PotionLockdown(true, 0x000000);
	
	public PotionLockdown(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName("lockdown");
		this.setPotionName("factorytech.potion.lockdown");
		this.setIconIndex(0, 0); // Icon is irrelevant since it is mob-only
	}
	
	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase toAffect, int amplifier, double health)
	{
		if (toAffect instanceof EntityMob)
		{
			((EntityMob)toAffect).setAttackTarget(null);
		}
	}
	
	public boolean hasEffect(EntityLivingBase entity)
	{
		return entity.getActivePotionEffect(this) != null;
	}
	
	public int getEffectLevel(EntityLivingBase entity)
	{
		if (!hasEffect(entity)) return -1;
		return entity.getActivePotionEffect(this).getAmplifier();
	}
}