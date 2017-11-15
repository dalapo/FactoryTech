package dalapo.factech.tileentity.specialized;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.tileentity.TileEntityBase;

public class TileEntityDecoCoil extends TileEntityBase {
	private BlockPos neighbour;
	private double[][] positions = new double[3][3];
	private double[][] velocities = new double[3][3];
	private long[] prevTimes = {0, 0, 0};
	private double[] targetTimes = {0, 0, 0};
	
	private static void randomOffset(double[] vec, double factor)
	{
//		Logger.info("Entered randomOffset");
		for (int i=0; i<vec.length; i++)
		{
			vec[i] += (Math.random() - 0.5) * factor * 2;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, double x, double y, double z)
	{
		Tessellator v5 = Tessellator.getInstance();
		
		int tX = pos.getX();
		int tY = pos.getY();
		int tZ = pos.getZ();
		
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
		buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		if (neighbour != null && neighbour.getY() != 0 && world.isBlockIndirectlyGettingPowered(pos) == 0 && world.isBlockIndirectlyGettingPowered(neighbour) == 0)
		{
			double Px = neighbour.getX() - tX;
			double Py = neighbour.getY() - tY;
			double Pz = neighbour.getZ() - tZ;
			double distance = FacMathHelper.pyth3D(Px, Py, Pz);
			
			for (int i=0; i<3; i++)
			{
				if (positions[i] == null || world.getWorldTime() - prevTimes[i] >= targetTimes[i])
				{
					positions[i] = new double[] {Px*0.25*(i+1), Py*0.25*(i+1), Pz*0.25*(i+1)};
					velocities[i] = new double[] {0, 0, 0};
					randomOffset(positions[i], MathHelper.sqrt(distance)/20);
					randomOffset(velocities[i], 0.025);
					
					// Reset times
					prevTimes[i] = world.getWorldTime();
					targetTimes[i] = (Math.random() + 0.5) * 5;
				}
			}
			
			buffer.pos(0, 0.1, 0).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
			
			double d = (world.getWorldTime() % 20) + partialTicks;
			for (int i=0; i<3; i++)
			{
				buffer.pos(positions[i][0] + d*velocities[i][0], positions[i][1] + d*velocities[i][1], positions[i][2] + d*velocities[i][2]).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
			}
			buffer.pos(Px, Py, Pz).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
		}
		v5.draw();
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
	
	public BlockPos getNeighbour()
	{
		return neighbour;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}
	
	public void setNeighbour(BlockPos other)
	{
		if (!(this.pos.equals(other)))
		{
			neighbour = other;
			if (world.getTileEntity(other) instanceof TileEntityDecoCoil)
			{
				((TileEntityDecoCoil)world.getTileEntity(other)).neighbour = pos;
			}
		}
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (neighbour != null) nbt.setLong("pos", neighbour.toLong());
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		neighbour = (BlockPos.fromLong(nbt.getLong("pos")));
	}
}