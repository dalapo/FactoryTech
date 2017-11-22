package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.helper.DecoCoilLink;
import dalapo.factech.helper.DecoCoilLinkGraph;
import dalapo.factech.helper.DecoCoilLinkGraph.DCLG;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityDecoCoil extends TileEntityBase implements ITickable {
	private DecoCoilLinkGraph links;
	// we use this to allow linking upon game load.
	// This is because first, the TE loads with world == null - so we can't immediately check
	// to see if there's another loaded DecoCoil at the linked positions. Consequently, we link
	// to our connections on the first call to update().
	private List<BlockPos> nbtConnections;
	private boolean connected = false;
	
	private double[][] positions = new double[3][3];
	private double[][] velocities = new double[3][3];
	private long[] prevTimes = {0, 0, 0};
	private double[] targetTimes = {0, 0, 0};
	
	private static final Vec3d HALFBLOCK = new Vec3d(0.5f, 0.5f, 0.5f);
	private static final String NEIGHBOURS_NBTKEY = "TEDCNeighbours";
	
	public TileEntityDecoCoil()
	{
		super();
		links = new DecoCoilLinkGraph();
		links.addVertex(this);
	}
	
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, double x, double y, double z)
	{
		Tessellator v5 = Tessellator.getInstance();
		
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GlStateManager.disableAlpha();
		GlStateManager.enableCull();
		GlStateManager.depthMask(false);
		
		GlStateManager.glLineWidth(3.0F);
		BufferBuilder buffer = v5.getBuffer();
		if (!this.isPoweredThroughConnections())
		{
			for (DecoCoilLink link : links.getOutgoingLinks(this))
			{
				buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
				link.update(world);
				link.draw(world, buffer, partialTicks);
				v5.draw();
			}
		}
		GlStateManager.depthMask(true);
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}
	
	/**
	 * Called every tick. 
	 */
	@Override
	public void update()
	{
		// link to other TileEntityDecoCoils on first call to update() after reading from NBT.
		// For some reason, putting this in onLoad() doesn't work. That could be indicative of poor coding, or just MC quirks..
		if (!connected && world != null && nbtConnections != null)
		{
			for (BlockPos blockPos : nbtConnections)
			{
				if (world.getTileEntity(blockPos) instanceof TileEntityDecoCoil)
				{
					TileEntityDecoCoil.link(this, (TileEntityDecoCoil) world.getTileEntity(blockPos));
				}
			}
			connected = true;
		}
		
		// Disable visuals & hurting things if any connected coil is powered.
		if (!this.isPoweredThroughConnections())
		{
			Vec3d centerVec = new Vec3d(this.getPos()).add(HALFBLOCK);
			for (TileEntityDecoCoil other : links.getNeighbours(this))
			{
				Vec3d neighbourVec = new Vec3d(other.getPos()).add(HALFBLOCK);
				List<EntityLivingBase> nearbyEntities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(centerVec, neighbourVec));
				for (EntityLivingBase entity : nearbyEntities)
				{
					if (entity.getEntityBoundingBox() != null && entity.getEntityBoundingBox().calculateIntercept(centerVec, neighbourVec) != null)
					{
						affectEntity(entity);
					}
				}
			}
		}
	}
	
	/**
	 * This function is called every tick on every entity between this coil and its linked coil(s).
	 * 
	 * @param entity The entity to affect.
	 */
	private void affectEntity(EntityLivingBase entity)
	{
		entity.attackEntityFrom(DamageSource.IN_FIRE, 1.0f);
	}
	
	/**
	 * @return boolean true if any connected TEDC is powered, false otherwise.
	 */
	private boolean isPoweredThroughConnections()
	{
		return links.isPowered(this);
	}
	
	/**
	 * This function is called when any TileEntity is destroyed. Removes itself from the link graph.
	 */
	@Override
	public void invalidate()
	{
		super.invalidate();
		links.removeVertex(this);
	}
	
	/**
	 * Static function for linking two TileEntityDecoCoils.
	 * 
	 * @param first The TileEntityDecoCoil to link from.
	 * @param second The TileEntityDecoCoil to link to.
	 */
	public static void link(TileEntityDecoCoil first, TileEntityDecoCoil second)
	{
		first.links.merge(second.links);
		first.links.addEdge(first, second);
	}
	
	/**
	 * Sets the current link graph to be the given link graph. This function will only work if a non-null DCLG is passed.
	 * This can only be done within the DecoCoilLinkGraph class, because DCLG has a private constructor. Hence, this
	 * function is effectively private except to DecoCoilLinkGraph objects.
	 * 
	 * @param newLinks The new link graph to be a part of.
	 * @param privacyGuarantee A parameter that can only be created by the DecoCoilLinkGraph function. If non-null, the call was from DecoCoilLinkGraph.
	 */
	public void setLinks(DecoCoilLinkGraph newLinks, DecoCoilLinkGraph.DCLG privacyGuarantee)
	{
		if (privacyGuarantee == null) return; // or throw exception?
		this.links = newLinks;
		this.links.addVertex(this); // just to be sure.
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (links != null)
		{
			NBTTagList tagList = new NBTTagList();
			for (TileEntityDecoCoil neighbour : links.getNeighbours(this))
			{
				tagList.appendTag(new NBTTagLong(neighbour.getPos().toLong()));
			}
			nbt.setTag(NEIGHBOURS_NBTKEY, tagList);
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		nbtConnections = new ArrayList<BlockPos>();
		if (nbt.hasKey(NEIGHBOURS_NBTKEY))
		{
			NBTTagList neighbourTagList = (NBTTagList)nbt.getTag(NEIGHBOURS_NBTKEY);
			for (int i = 0; i < neighbourTagList.tagCount(); i++)
			{
				nbtConnections.add(BlockPos.fromLong(((NBTTagLong)neighbourTagList.get(i)).getLong()));
			}
		}
		connected = false;
	}
}