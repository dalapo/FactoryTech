package dalapo.factech.tileentity.specialized;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;
import dalapo.factech.auxiliary.MachineRecipes;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.packet.MachineInfoPacket;
import dalapo.factech.packet.PacketFactory;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import dalapo.factech.tileentity.TileEntityMachine;

public class TileEntityCrucible extends TileEntityFluidMachine {

	public TileEntityCrucible() {
		super("crucible", 1, 2, 1, 1, 0, RelativeSide.BOTTOM);
		setDisplayName("Crucible");
//		tanks[0].setCanFill(false);
	}

	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.HEATELEM, 0.1F, 1.15F, 0.8F, 6);
		partsNeeded[1] = new MachinePart(PartList.MIXER, 0.15F, 1.15F, 16);
	}
	
	
	
	protected boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	public void getHasWork()
	{
		if (tanks == null) return;
		FluidStack potentialOut = getOutput(getInput(0));
		if (potentialOut == null) hasWork = false;
		else if (tanks[0].getFluid() == null || (tanks[0].getFluid().isFluidEqual(potentialOut) && tanks[0].getFluidAmount() + potentialOut.amount <= 10000)) hasWork = true;
		else hasWork = false;
	}

	@Override
	protected boolean performAction()
	{
		Logger.info(getOutput(getInput(0)));
		tanks[0].fillInternal(getOutput(getInput(0)), true);
		getInput(0).shrink(1);
		sendInfoPacket();
		getHasWork();
		return true;
	}

	protected FluidStack getOutput(ItemStack is)
	{
		for (Entry<ItemStack, FluidStack> entry : getRecipeList().entrySet())
		{
			ItemStack in = entry.getKey().copy();
			FluidStack out = entry.getValue().copy();
			if (FacStackHelper.matchStacksWithWildcard(in, is, true) && in.getCount() <= is.getCount())
			{
				return out;
			}
		}
		return null;
	}
	
	public Map<ItemStack, FluidStack> getRecipeList() {
		return MachineRecipes.CRUCIBLE;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 80;
	}

	@Override
	public void onTankUpdate() {
		// TODO Auto-generated method stub
		
	}
}
