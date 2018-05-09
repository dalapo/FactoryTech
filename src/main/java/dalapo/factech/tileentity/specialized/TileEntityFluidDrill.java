package dalapo.factech.tileentity.specialized;

import java.util.Random;

import dalapo.factech.config.FacTechConfigManager;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.init.BlockRegistry;
import dalapo.factech.init.ModFluidRegistry;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.packet.PlayerChatPacket;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import dalapo.factech.tileentity.TileEntityMachineNoOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityFluidDrill extends TileEntityFluidMachine {

	private boolean drillSulphur;
	private int mbPerOperation;
	public TileEntityFluidDrill() {
		super("fluiddrill", 0, 3, 0, 1, 0, RelativeSide.ANY);
		setDisplayName("Fluid Drill");
	}
	
	@Override
	public void onLoad()
	{
		Random rand = world.getChunkFromBlockCoords(pos).getRandomWithSeed(0);
		drillSulphur = rand.nextBoolean();
		mbPerOperation = rand.nextInt(150) + 10;
		if (rand.nextInt(4) == 0) mbPerOperation += rand.nextInt(100);
		getHasWork();
//		PacketHandler.sendToAll(new PlayerChatPacket(String.format("Sulphur: %s; %s mB per operation", drillSulphur, mbPerOperation)));
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Fluid Drill");
	}
	
	@Override
	public void getHasWork()
	{
		for (int i=1; i<pos.getY(); i++)
		{
			if (world.getBlockState(FacMathHelper.withOffsetAndDist(pos, EnumFacing.DOWN, i)).getBlock() != Blocks.BEDROCK || !world.isAirBlock(FacMathHelper.withOffsetAndDist(pos, EnumFacing.DOWN, i)))
			{
				hasWork = false;
			}
			if (i == pos.getY()) break;
		}
		hasWork = true;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && tanks[0].getFluidAmount() <= (10000 - mbPerOperation) && hasWork;
	}
	
	@Override
	protected boolean performAction()
	{	
		Fluid fluid = drillSulphur ? ModFluidRegistry.sulphur : ModFluidRegistry.propane;
		tanks[0].fillInternal(new FluidStack(fluid, (int)(mbPerOperation * FacTechConfigManager.fluidDrillMultiplier)), true);
		return true;
	}

	@Override
	public int getOpTime() {
		return 30;
	}

	@Override
	public void onTankUpdate() {
		
	}
	
	public boolean isSulphur()
	{
		return drillSulphur;
	}
	
	public int amountPer()
	{
		return mbPerOperation;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("sulphur", drillSulphur);
		nbt.setInteger("mb", mbPerOperation);
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		drillSulphur = nbt.getBoolean("sulphur");
		mbPerOperation = nbt.getInteger("mb");
	}
}
