package dalapo.factech.entity;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class EntityPressureGunShot extends EntityArrow
{
	private EnumPressureGun projType = EnumPressureGun.AIR;
	
	private int bX = -1;
	private int bY = -1;
	private int bZ = -1;
	private double distTravelled = 0;
	private int age = 0;
	
	public EntityPressureGunShot(World world)
	{
		super(world);
	}
	
	public EntityPressureGunShot(World worldIn, EntityLivingBase shooter, int fluidType) {
		super(worldIn);
		this.setPosition(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ);
		try {
		projType = EnumPressureGun.values()[fluidType];
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			Logger.info("Array index out of bounds: " + fluidType);
			projType = EnumPressureGun.AIR;
		}
	}
	
	public Fluid getFluid()
	{
		Logger.info(projType.name);
		switch (projType)
		{
		case WATER:
			return FluidRegistry.WATER;
		case H2SO4:
			return ModFluidRegistry.h2so4;
		case PROPANE:
			return FluidRegistry.LAVA;
		default:
			return null;
		}
	}
	
	@Override
	public void onHit(RayTraceResult raytrace)
	{
		if (raytrace.entityHit instanceof EntityLiving)
		{
			EntityLiving entityHit = (EntityLiving)raytrace.entityHit;
			if (projType.damage > 0)
			{
				entityHit.attackEntityFrom(new DamageSource("fluidgun"), projType.damage);
			}
			Vec3d knockback = new Vec3d(motionX, motionY + 0.2, motionZ).normalize().scale(projType.knockback);
			entityHit.addVelocity(knockback.x, knockback.y, knockback.z);
			if (projType.fire)
			{
				entityHit.setFire(5);
			}
//			setDead();
		}
		else
		{
//			setDead();
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

            if (this.isInWater())
            {
                for (int i = 0; i < 4; ++i)
                {
                    float f3 = 0.25F;
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
                }

                f1 = 0.6F;
            }

            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= (double)f1;
            this.motionY *= (double)f1;
            this.motionZ *= (double)f1;

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
		AIR("air", 0, 3.0F, 4, 0.2, false),
		WATER("water", 1, 1.0F, 10, 0, false),
		PROPANE("propane", 5, 0.5F, 5, 0.15, true),
		H2SO4("h2so4", 8, 0.8F, 6, 0.1, false);
		
		private EnumPressureGun(String name, int damage, float knockback, int range, double spread, boolean fire)
		{
			this.name = name;
			this.damage = damage;
			this.knockback = knockback;
			this.range = range;
			this.spread = spread;
			this.fire = fire;
		}
		
		public final String name;
		public final int damage;
		public final float knockback;
		public final int range;
		public final double spread;
		public final boolean fire;
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