package dalapo.factech.entity;

import javax.annotation.Nullable;

import dalapo.factech.auxiliary.MachinePart;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHoverScooter extends Entity
{
	private MachinePart[] partsNeeded;
	private int[] partsGot;
	private int age;
	
	public EntityHoverScooter(World world)
	{
		super(world);
		setSize(1.375F, 0.5F);
		age = 0;
		preventEntitySpawning = true;
//		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public EntityHoverScooter(World world, BlockPos toSpawn)
	{
		this(world);
//		Logger.info("Constructing hover scooter on thread " + Thread.currentThread());
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		preventEntitySpawning = true;
		this.setPosition(toSpawn.getX()+0.5, toSpawn.getY()+0.5, toSpawn.getZ()+0.5);
		this.setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 1.375F, 0.5F, 1.37F).offset(posX, posY, posZ));
		prevPosX = toSpawn.getX()+0.5;
		prevPosY = toSpawn.getY()+0.5;
		prevPosZ = toSpawn.getZ()+0.5;
//		Logger.info(this.getEntityBoundingBox().toString());
	}
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
		return this.getEntityBoundingBox();
    }
	
	@Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return entityIn.getEntityBoundingBox();
    }
	
	
	@Override
	public boolean canBePushed()
	{
		return true;
	}
	
	@Override
	protected boolean canBeRidden(Entity entity)
	{
		return (!isBeingRidden() && entity instanceof EntityLivingBase);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount)
	{
		this.setDead();
		return true;
	}
	
	
//	@Override
//	public EnumActionResult applyPlayerInteraction(EntityPlayer ep, Vec3d vec, EnumHand hand)
//	{
//		super.applyPlayerInteraction(ep, vec, hand);
//		processInitialInteract(ep, hand);
//		return EnumActionResult.SUCCESS;
//	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
	{
		Logger.info("Right-click detected");
		if (player.isSneaking())
		{
			// Open part maintenance GUI
		}
		else if (!isBeingRidden())
		{
			player.startRiding(this);
			return true;
		}
		return false;
	}
	
	private double getDistanceToGround()
	{
		AxisAlignedBB aabb = this.getEntityBoundingBox();
		int minX = MathHelper.floor(aabb.minX);
		int maxX = MathHelper.ceil(aabb.maxX);
		int minY = MathHelper.floor(aabb.minY);
		int maxY = MathHelper.ceil(aabb.maxY);
		int minZ = MathHelper.floor(aabb.minZ);
		int maxZ = MathHelper.ceil(aabb.maxZ);
		BlockPos.PooledMutableBlockPos pmbp = BlockPos.PooledMutableBlockPos.retain();
		int dist = 0;
		try
		{
			j:
			for (int j=minY; j>0; j--, dist++)
			{
				for (int i=minX; i<maxX; i++)
				{
					for (int k=minZ; k<maxZ; k++)
					{
						pmbp.setPos(i, j, k);
						if (!world.isAirBlock(pmbp)) break j;
					}
				}
			}
		}
		finally
		{
//			Logger.info("Releasing pmbp");
			pmbp.release();
		}
		return dist + aabb.minY;
	}
	private boolean isCloseToGround(double howClose)
	{
		AxisAlignedBB airCushion = this.getEntityBoundingBox().offset(0, -howClose, 0);
		int minX = MathHelper.floor(airCushion.minX);
		int maxX = MathHelper.ceil(airCushion.maxX);
		int minY = MathHelper.floor(airCushion.minY);
		int maxY = MathHelper.ceil(airCushion.maxY);
		int minZ = MathHelper.floor(airCushion.minZ);
		int maxZ = MathHelper.ceil(airCushion.maxZ);
		
		BlockPos.PooledMutableBlockPos pmbp = BlockPos.PooledMutableBlockPos.retain();
		boolean flag = false;
		try
		{
			for (int i=minX; i<maxX; i++)
			{
				for (int j=minY; j<maxY; j++)
				{
					for (int k=minZ; k<maxZ; k++)
					{
						pmbp.setPos(i, j, k);
						if (!world.isAirBlock(pmbp)) flag = true;
					}
				}
			}
		}
		finally
		{
//			Logger.info("Releasing pmbp");
			pmbp.release();
		}
		return flag;
	}
	
	@Override
	public void onUpdate()
	{
//		Logger.info(this.getEntityBoundingBox());
		if (isCloseToGround(1.5)) motionY += 0.05;
//		if (++age % 10 == 0) Logger.info(getPosition()); // Display position twice/second
		else motionY -= 0.05;
		
		motionY = FacMathHelper.clamp(motionY, -0.5, 0.5);
		
		motionX *= 0.99;
		motionY *= 0.75;
		motionZ *= 0.99;
		
//		posX += motionX;
//		posY += motionY;
//		posZ += motionZ;
		this.setPosition(posX+motionX, posY+motionY, posZ+motionZ);
//		this.setPositionAndUpdate(posX+motionX, posY+motionY, posZ+motionZ);
//		this.moveRelative((float)motionX, (float)motionY, (float)motionZ, 0.9F);
//		this.doBlockCollisions();
		super.onUpdate();
		// Hover 1 block above the ground if being ridden by a player.
	}
	
	@Override
	protected void entityInit()
	{
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
	}
	
//	@SubscribeEvent
//	public EnumActionResult rideScooter(EntityInteractSpecific evt)
//	{
//		Logger.info("I love my scooter!");
//		if (evt.getTarget() == this)
//		{
//			this.processInitialInteract(evt.getEntityPlayer(), evt.getHand());
//			return EnumActionResult.SUCCESS;
//		}
//		return EnumActionResult.PASS;
//	}
//	
//	// The closest thing there is to a Destructor
//	@Override
//	public void setDead()
//	{
//		MinecraftForge.EVENT_BUS.unregister(this);
//		super.setDead();
//	}
}