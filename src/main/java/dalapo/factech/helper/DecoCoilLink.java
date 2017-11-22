package dalapo.factech.helper;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DecoCoilLink {

	private Vec3d start;
	private Vec3d end;
	private Vec3d delta;
	private double distance;
	private double[][] positions = new double[3][3];
	private double[][] velocities = new double[3][3];
	private long[] prevTimes = {0, 0, 0};
	private double[] targetTimes = {0, 0, 0};
	
	public DecoCoilLink(Vec3d startPoint, Vec3d endPoint)
	{
		start = startPoint;
		end = endPoint;
		delta = end.subtract(start);
		distance = delta.lengthVector();
	}
	
	public DecoCoilLink(BlockPos startPoint, BlockPos endPoint)
	{
		this(new Vec3d(startPoint), new Vec3d(endPoint));
	}

	public void update(World world)
	{
		for (int i=0; i<3; i++)
		{
			if (positions[i] == null || world.getTotalWorldTime() - prevTimes[i] >= targetTimes[i])
			{
				positions[i] = new double[] {delta.x*0.25*(i+1), delta.y*0.25*(i+1), delta.z*0.25*(i+1)};
				velocities[i] = new double[] {0, 0, 0};
				randomOffset(positions[i], MathHelper.sqrt(distance)/20);
				randomOffset(velocities[i], 0.025);
				
				// Reset times
				prevTimes[i] = world.getTotalWorldTime();
				targetTimes[i] = (Math.random() + 0.5) * 50.0d;
			}
		}
	}
	
	public void draw(World world, BufferBuilder buffer, double partialTicks)
	{
		buffer.pos(0, 0.1, 0).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
		
		
		for (int i=0; i<3; i++)
		{
			double d = ((world.getWorldTime() + targetTimes[i]) % 20) + partialTicks;
			buffer.pos(positions[i][0] + d*velocities[i][0], positions[i][1] + d*velocities[i][1], positions[i][2] + d*velocities[i][2]).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
		}
		
		buffer.pos(delta.x, delta.y, delta.z).color(0.5F, 0.5F, 1.0F, 1.0F).endVertex();
	}
	
	@Override
	public String toString()
	{
		return String.format("DecoCoilLink{start: %s, end: %s}", start, end);
	}
	
	/**
	 * Adds a random value between -factor and +factor to every value in vec.
	 * 
	 * @param vec Array of doubles to add an offset to to affect.
	 * @param factor Maximum value to offset by.
	 */
	private static void randomOffset(double[] vec, double factor)
	{
//		Logger.info("Entered randomOffset");
		for (int i=0; i<vec.length; i++)
		{
			vec[i] += (Math.random() - 0.5) * factor * 2;
		}
	}
}
