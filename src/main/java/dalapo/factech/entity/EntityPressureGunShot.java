package dalapo.factech.entity;

import javax.annotation.Nullable;

import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.ModFluidRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class EntityPressureGunShot extends EntityArrow
{
	private EnumPressureGun projType = EnumPressureGun.AIR;
	
	private EntityLivingBase owner;
	private boolean isFresh;
	private int bX = -1;
	private int bY = -1;
	private int bZ = -1;
	private double distTravelled = 0;
	private int age = 0;
	
	public EntityPressureGunShot(World world)
	{
		super(world);
	}
	
	public EntityPressureGunShot(World worldIn, EntityLivingBase shooter, int fluidType, boolean flag) {
		super(worldIn);
		owner = shooter;
		isFresh = flag;
		this.setPosition(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
		try {
		projType = EnumPressureGun.values()[fluidType];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			Logger.warn("Array index out of bounds: " + fluidType);
			projType = EnumPressureGun.AIR;
		}
	}
	
	public Fluid getFluid()
	{
//		Logger.info(projType.name);
		switch (projType)
		{
		case WATER:
			return FluidRegistry.WATER;
		case H2SO4:
			return ModFluidRegistry.h2so4;
		case PROPANE:
			return FluidRegistry.LAVA;
		case GLOWSTONE:
			return ModFluidRegistry.glowstone;
		case SULPHUR:
			return ModFluidRegistry.sulphur;
		case ENERGITE:
			return ModFluidRegistry.energite;
		default:
			return null;
		}
	}
	
	@Override
	public void onHit(RayTraceResult raytrace)
	{
		if (raytrace.entityHit instanceof EntityLiving && raytrace.entityHit != owner)
		{
			EntityLiving entityHit = (EntityLiving)raytrace.entityHit;
			if (projType.damage > 0 && !(entityHit instanceof EntitySquid))
			{
				entityHit.attackEntityFrom(new EntityDamageSource(isFresh ? "splat" : "fluidgun", owner) {
					@Nullable
				    public Vec3d getDamageLocation()
				    {
				        return null;
				    }
				}, projType.damage);
			}
			Vec3d knockback = new Vec3d(motionX, motionY + 0.2, motionZ).normalize().scale(projType.knockback);
			if (entityHit.hurtTime == 0) entityHit.addVelocity(knockback.x, knockback.y, knockback.z);
			for (PotionEffect effect : projType.effects)
			{
				entityHit.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier()));
			}
			if (projType.fire)
			{
				entityHit.setFire(5);
			}
			if (entityHit.getHealth() <= 0 && owner instanceof EntityPlayer && isFresh)
			{
				FacChatHelper.sendStatusMessage((EntityPlayer)owner, "Splatted " + entityHit.getName() + "!", true);
			}
			setDead();
		}
	}

	public void onUpdate()
    {
        this.onEntityUpdate();

        BlockPos blockpos = new BlockPos(this.bX, this.bY, this.bZ);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();

        if (iblockstate.getMaterial() != Material.AIR)
        {
            AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }
        if (this.inGround) setDead();
        else
        {
            ++this.age;
            Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
            vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
            vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (raytraceresult != null)
            {
                vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
            }

            Entity entity = this.findEntityOnPath(vec3d1, vec3d);

            if (entity != null)
            {
                raytraceresult = new RayTraceResult(entity);
            }

            if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;

                if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    raytraceresult = null;
                }
            }

            if (raytraceresult != null)
            {
                this.onHit(raytraceresult);
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            this.distTravelled += FacMathHelper.pyth3D(motionX, motionY, motionZ);
            float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

            for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f1 = 0.99F;
            float f2 = 0.05F;

            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= (double)f1;
            this.motionY *= (double)f1;
            this.motionZ *= (double)f1;

            if (this.projType.gravity)
            {
            	this.motionY -= 0.02;
            }
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
        if (inGround || distTravelled > projType.range)
        {
        	setDead();
        }
    }
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		if (compound.hasKey("fluidType"))
		{
			projType = EnumPressureGun.values()[compound.getInteger("fluidType")];
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("fluidType", projType.ordinal());
	}
	
	public EnumPressureGun getProjType()
	{
		return projType;
	}

	public static enum EnumPressureGun
	{
		AIR("air", 0, 1.5F, 6, 0.2, false, false),
		WATER("water", 1, 1.0F, 15, 0, false, true),
		PROPANE("propane", 5, 0.75F, 8, 0.15, true, false),
		H2SO4("h2so4", 8, 0.5F, 9, 0.1, false, true, new PotionEffect(MobEffects.RESISTANCE, 120, -1)),
		SULPHUR("sulphur", 4, 0.5F, 10, 0.15, false, true, new PotionEffect(MobEffects.SLOWNESS, 100, 2), new PotionEffect(MobEffects.WEAKNESS, 100, 2), new PotionEffect(MobEffects.NAUSEA, 120, 1)),
		GLOWSTONE("glowstone", 1, 0, 8, 0.05, false, true, new PotionEffect(MobEffects.GLOWING, 200, 1)),
		ENERGITE("energite", 6, 0.75F, 10, 0.075, false, true, new PotionEffect(MobEffects.POISON, 160, 3));
		
		private EnumPressureGun(String name, int damage, float knockback, int range, double spread, boolean fire, boolean gravity, PotionEffect... effects)
		{
			this.name = name;
			this.damage = damage;
			this.knockback = knockback;
			this.range = range;
			this.spread = spread;
			this.fire = fire;
			this.gravity = gravity;
			this.effects = effects;
		}
		
		public final String name;
		public final int damage;
		public final float knockback;
		public final int range;
		public final double spread;
		public final boolean fire;
		public final boolean gravity;
		public final PotionEffect[] effects;
	}

	@Override
	protected ItemStack getArrowStack() {
		// TODO Auto-generated method stub
		return ItemStack.EMPTY;
	}
	
	private EnumParticleTypes getParticle()
	{
		return EnumParticleTypes.WATER_SPLASH;
	}
	
	@Override
	public boolean getIsCritical()
	{
		return false;
	}
}