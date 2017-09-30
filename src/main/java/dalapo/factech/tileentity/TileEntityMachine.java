package dalapo.factech.tileentity;

import dalapo.factech.FactoryTech;
import dalapo.factech.helper.FacChatHelper;
import dalapo.factech.helper.FacMathHelper;
import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.helper.FacStackHelper;
import dalapo.factech.helper.FacTileHelper;
import dalapo.factech.helper.Logger;
import dalapo.factech.helper.Pair;
import dalapo.factech.init.ItemRegistry;
import dalapo.factech.item.ItemMachinePart;
import dalapo.factech.packet.MachineInfoPacket;
import dalapo.factech.packet.PacketHandler;
import dalapo.factech.reference.PartList;
import dalapo.factech.reference.StateList;
import dalapo.factech.tileentity.automation.TileEntityCrate;
import dalapo.factech.tileentity.specialized.TileEntityEnergizer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityMachine extends TileEntityBasicInventory implements ITickable, ISidedInventory {
	
	protected enum RelativeSide {
		FRONT,
		BACK,
		SIDE,
		TOP,
		BOTTOM,
		ANY,
		NONE
	}
	
	protected boolean shouldUpdate;
	protected MachinePart[] partsNeeded;
	protected boolean[] partsGot;
	protected int opTime;
	protected int partSlots;
	protected int inSlots;
	protected int outSlots;
	protected boolean hasWork;
	protected boolean isRunning; // Mostly for TESRs
	protected boolean isDisabledByRedstone = false;
	protected RelativeSide partSide;
	
	public boolean isCharged;
	
	/**
	 * 0: No upgrade
	 * 1: Overclock
	 * 2: Underclock
	 * 3: Range
	 * 4: Consistency
	 * 5: Efficiency
	 */
	protected int installedUpgrade;
	public int age;
	
	public TileEntityMachine(String name, int inSlots, int partSlots, int outSlots, RelativeSide partSide)
	{
		super(name, inSlots + partSlots + outSlots);
		setDisplayName(FacMiscHelper.capitalizeFirstLetter(name));
		this.name = name;
		this.opTime = getOpTime();
		this.inSlots = inSlots;
		this.partSlots = partSlots;
		this.outSlots = outSlots;
		this.partSide = partSide;
		initArrays();
		fillMachineParts();
		age = 0;
		installedUpgrade = 0;
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		getHasWork();
	}
	
	public void markDirtyLight()
	{
		if (world != null)
		{
			world.markChunkDirty(pos, this);
		}
	}
	
	protected boolean checkForEnergize()
	{
		boolean flag = false;
		for (int i=0; i<6; i++)
		{
			TileEntity te = world.getTileEntity(FacMathHelper.withOffset(pos, EnumFacing.getFront(i)));
			if (te instanceof TileEntityEnergizer && ((TileEntityEnergizer)te).isCharging())
			{
				flag = true;
			}
		}
		return flag;
	}
	
	private double getAdjustedChance(double baseChance)
	{
		if (installedUpgrade != 4 || baseChance >= 1.0) return baseChance;

		double d = (1.0 - baseChance) / 2;
		return 1.0 - d;
	}

	public void update() {
		if (canRun())
		{
			isRunning = true;
			if (isCharged) ++age;
			if (++age >= getActualOptime())
			{
				age = 0;
				if (!world.isRemote)
				{
					
					isCharged = checkForEnergize();
					if (performAction())
					{
						for (int i=0; i<partSlots; i++)
						{
//							if (!partsNeeded[i].getSalvage().isEmpty()) Logger.info(String.format("Potential salvage in slot %s: %s", i, partsNeeded[i].getSalvage()));
							if (partsNeeded[i].shouldBreak())
							{
								partsGot[i] = false;
								if (FactoryTech.random.nextDouble() < getAdjustedChance(partsNeeded[i].salvageChance))
								{
									boolean flag = false;
									Pair<EnumFacing, TileEntity> cratePos = FacTileHelper.getFirstAdjacentTile(pos, world, TileEntityCrate.class);
									if (cratePos != null)
									{
										IItemHandler storage = ((TileEntityCrate)cratePos.b).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, cratePos.a.getOpposite());
										if (FacTileHelper.hasSpaceForItem(storage, partsNeeded[i].getSalvage(), cratePos.a.getOpposite(), false))
										{
											flag = true;
											FacTileHelper.tryInsertItem(storage, partsNeeded[i].getSalvage().copy(), cratePos.a.getOpposite().ordinal());
										}
									}
									if (!flag)
									{
										EntityItem ei = new EntityItem(world, pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5, partsNeeded[i].getSalvage().copy());
										ei.motionX = 0;
										ei.motionY = 0;
										ei.motionZ = 0;
										world.spawnEntity(ei);
									}
								}
							}
							else
							{
								partsNeeded[i].increaseChance();
							}
						}
					}
					markDirty();
					syncToAll();
				}
			}
		}
		else
		{
			age = 0;
			isRunning = false;
		}
		for (int i=0; i<partSlots; i++)
		{
			if (partsGot[i] == false)
			{
				int slot = containsItem(partsNeeded[i].getItem(), inSlots, inSlots + partSlots);
				if (slot != -1)
				{
					decrStackSize(slot, 1);
					partsNeeded[i].reset();
					partsGot[i] = true;
					markDirty();
				}
			}
		}
	}
	
	public void installUpgrade(int upgrade)
	{
		if (installedUpgrade != 0)
		{
			ItemStack oldUpgrade = new ItemStack(ItemRegistry.upgrade, 1, installedUpgrade - 1);
			world.spawnEntity(new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, oldUpgrade));
		}
		installedUpgrade = upgrade;
		syncToAll();
		markDirty();
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		super.onDataPacket(net, packet);
		getHasWork();
	}
	
	private int getActualOptime()
	{
		switch (installedUpgrade)
		{
		case 1:
			return opTime / 2;
		case 2:
			return opTime * 2;
			default:
				return opTime;
		}
	}
	
	protected boolean doOutput(ItemStack out, int outSlot)
	{
		if (getOutput(outSlot).isEmpty())
		{
			setOutput(outSlot, out);
			return true;
		}
		if (FacStackHelper.canCombineStacks(getOutput(outSlot), out))
		{
			getOutput(outSlot).grow(out.getCount());
			return true;
		}
		return false;
	}
	
	protected boolean doOutput(ItemStack out)
	{
		for (int i=0; i<outSlots; i++)
		{
			if (getOutput(i).isEmpty())
			{
				setOutput(out);
				return true;
			}
			if (getOutput(i).isItemEqual(out) && getOutput(i).getCount() + out.getCount() <= FacMathHelper.getMin(getInventoryStackLimit(), out.getMaxStackSize()))
			{
				getOutput(i).grow(out.getCount());
				return true;
			}
		}
		return false;
	}
	
	protected boolean doOutput(NonNullList<ItemStack> outs)
	{
		boolean success = true;
		for (ItemStack is : outs)
		{
			if (!doOutput(is)) success = false;
		}
		return success;
	}
	
	public void getHasWork()
	{
		hasWork = true;
	}
	
	@Override
	public int getSlotLimit(int slot)
	{
		if (!FacMathHelper.isInRange(slot, 0, getSizeInventory())) return -1;
		if (slot < inSlots || slot >= inSlots + partSlots) return 64;
		return 4;
	}
	
	public String getContainerName()
	{
		return name;
	}
	
	public ItemStack getInput(int i)
	{
		if (i < inSlots && i >= 0) return getStackInSlot(i);
		return null;
	}
	
	public ItemStack getInput()
	{
		return getInput(0);
	}
	
	public ItemStack getBuffer(int i)
	{
		if (i < partSlots && i >= 0) return getStackInSlot(i + inSlots);
		return null;
	}
	
	@Deprecated
	public int getProgressScaled()
	{
		return getProgressScaled(21);
	}
	
	public int getProgressScaled(int pixelSize)
	{
		return (int)(((double)age / getActualOptime()) * pixelSize);
	}
	
	public ItemStack getOutput(int slot)
	{
		if (slot >= 0 && slot < outSlots) return getStackInSlot(inSlots + partSlots + slot);
		return ItemStack.EMPTY;
	}
	
	public ItemStack getOutput()
	{
		return getOutput(0);
	}
	
	protected void syncToAll()
	{
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
	
	public void sendInfoPacket(EntityPlayer ep)
	{
		MachineInfoPacket packet = new MachineInfoPacket(this);
		PacketHandler.sendToPlayer(packet, (EntityPlayerMP)ep);
	}
	
	public void sendInfoPacket()
	{
		MachineInfoPacket packet = new MachineInfoPacket(this);
		PacketHandler.sendToServer(packet);
	}
		
	protected void setOutput(int slot, ItemStack is)
	{
		if (slot >= 0 && slot < outSlots) setInventorySlotContents(inSlots + partSlots + slot, is);
	}
	
	protected void setOutput(ItemStack is)
	{
		setOutput(0, is);
	}
	
	public int countPartSlots()
	{
		return partSlots;
	}
	
	public ItemStack getPart(int slot)
	{
		if (FacMathHelper.isInRange(slot, 0, partsNeeded.length))
		{
			return partsNeeded[slot].getItem();
		}
		return ItemStack.EMPTY;
	}
	
	public PartList getPartID(int slot)
	{
		if (FacMathHelper.isInRange(slot, 0, partsNeeded.length))
		{
			ItemStack part = partsNeeded[slot].getItem();
//			Logger.info(part);
			if (part.getItem() instanceof ItemMachinePart)
			{
				return PartList.values()[part.getItemDamage()];
			}
			return PartList.NOT_A_PART;
		}
		return null;
	}
	
	public boolean hasPart(int slot)
	{
		if (FacMathHelper.isInRange(slot, 0, partsGot.length)) return partsGot[slot];
		return false;
	}
	
	public void copyParts(boolean[] parts)
	{
		try {
			for (int i=0; i<partsGot.length; i++)
			{
				partsGot[i] = parts[i];
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			Logger.warn("copyParts called with an insufficient array");
		}
	}
	
	public int getInventoryStackLimit() {
		return 64;
	}
	
	public void showChatInfo(EntityPlayer ep)
	{
		String str = "Installed upgrade: ";
		switch (installedUpgrade)
		{
		case 1:
			str += "Overclock";
			break;
		case 2:
			str += "Underclock";
			break;
		case 3:
			str += "Range";
			break;
		case 4:
			str += "Consistency";
			break;
		case 5:
			str += "Efficiency";
			break;
		default:
			str += "None";
		}
		FacChatHelper.sendChatToPlayer(ep, str);
	}
	
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == getSizeInventory() - outSlots) return false;
		if (index < inSlots) return true;
		return stack.getItem() instanceof ItemMachinePart;
	}

	public int[] getSlotsForFace(EnumFacing side) {
		int[] slots = new int[inSlots + partSlots + outSlots];
		for (int i=0; i<slots.length; i++)
		{
			slots[i] = i;
		}
		return slots;
	}
	
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		EnumFacing orientation = world.getBlockState(getPos()).getValue(StateList.directions);
		boolean isPartSide = false;
		switch (partSide)
		{
		case NONE:
			isPartSide = false;
			break;
		case ANY:
			isPartSide = true;
			break;
		case FRONT:
			if (orientation == direction) isPartSide = true;
			break;
		case BACK:
			if (orientation == direction.getOpposite()) isPartSide = true;
			break;
		case SIDE:
			if (orientation != direction &&
			orientation != direction.getOpposite() &&
			orientation != EnumFacing.UP &&
			orientation != EnumFacing.DOWN) isPartSide = true;
			break;
		case TOP:
			if (direction.equals(EnumFacing.UP)) isPartSide = true;
			break;
		case BOTTOM:
			if (direction.equals(EnumFacing.DOWN)) isPartSide = true;
			break;
		}
//		Logger.info(String.format("Index: %s; %s", index, isPartSide));
		if (isPartSide) return (index >= inSlots && index < inSlots + partSlots);
		else return index < inSlots;
		/*
		if (direction == world.getBlockState(getPos()).getValue(StateList.directions).getOpposite())
		{
			// The back is specifically for inserting parts
			// All machines will have some indicator of which side is the back
			return (index >= inSlots && index < inSlots + partSlots);
		}
		else return index < inSlots;
		*/
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return (index >= inSlots + partSlots);
	}

	private void initArrays()
	{
		partsGot = new boolean[partSlots];
		partsNeeded = new MachinePart[partSlots];
	}
	
	protected boolean canRun()
	{
		if (isDisabledByRedstone && world.isBlockIndirectlyGettingPowered(pos) > 0) return false;
		for (boolean b : partsGot)
		{
			if (!b) return false;
		}
		return true;
//		return hasWork; (For some reason this doesn't work???)
	}
	
	public void setField(int id, int val)
	{
		if (id == 0) age = val;
		else if (id < getFieldCount())
		{
			partsGot[id] = val == 0 ? false : true;
		}
	}
	
	public int getField(int id)
	{
		if (id == 0) return age;
		else if (id < getFieldCount())
		{
			return partsGot[id] ? 1 : 0;
		}
		return 0;
	}
	
	public int getFieldCount()
	{
		return partsGot.length + 1;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i=0; i<partsGot.length; i++)
		{
			list.appendTag(new NBTTagByte((byte)(partsGot[i] ? 1 : 0)));
		}
		nbt.setTag("parts", list);
		nbt.setInteger("age", age);
		nbt.setBoolean("haswork", hasWork);
		nbt.setInteger("upgrade", installedUpgrade);
		nbt.setBoolean("isCharged", isCharged);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if (nbt.hasKey("parts"))
		{
			NBTTagList list = nbt.getTagList("parts", 1);
			for (int i=0; i<list.tagCount(); i++)
			{
				NBTTagByte tag = (NBTTagByte)(list.get(i));
				if (tag.getByte() == 1) partsGot[i] = true;
				else partsGot[i] = false;
			}
		}
		if (nbt.hasKey("age"))
		{
			age = nbt.getInteger("age");
		}
		if (nbt.hasKey("haswork")) hasWork = nbt.getBoolean("haswork");
		if (nbt.hasKey("upgrade")) installedUpgrade = nbt.getInteger("upgrade");
		if (nbt.hasKey("isCharged")) isCharged = nbt.getBoolean("isCharged");
	}
	
	/**
	 * Defines the array of replaceable machine parts, their base break chance,
	 * and their break chance increase per operation.
	 */
	protected abstract void fillMachineParts();
	
	/**
	 * Defines what an operation is for the machine. Returns true if the action was successful.
	 */
	protected abstract boolean performAction();
	
	/**
	 * Returns the time taken for the machine to complete an operation.
	 * @return Operation time in ticks
	 */
	public abstract int getOpTime();
	
	@Override
	protected void onInventoryChanged(int slot)
	{
//		Logger.info("Entered onInventoryChanged");
		syncToAll();
		getHasWork();
	}
	
	protected EnumFacing getFront()
	{
		return world.getBlockState(getPos()).getValue(StateList.directions);
	}
	
	public class MachinePart
	{
		private ItemStack id;
		private ItemStack depleted;
		
		private int numOperations = 0;
		private int minOperations;
		private float curBreakChance; // 1 = 100%
		private float baseBreakChance;
		private float salvageChance;
		private float increase;
		
		public MachinePart(ItemStack id, ItemStack salvage, float base, float inc, float chance, int minOperations)
		{
			if (base < 0 || inc < 0) throw new IllegalArgumentException ("Base and increase degrade chances must both be positive!");
			if (inc < 1) Logger.warn("Detected increase value < 1, meaning the break chance will decrease every operation. Is this intentional?");
			this.id = id;
			this.minOperations = minOperations;
			numOperations = 0;
			baseBreakChance = base;
			curBreakChance = base;
			increase = inc;
			depleted = salvage;
			salvageChance = chance;
		}
		
		public MachinePart(ItemStack is, float base, float inc, int numOperations)
		{
			this(is, ItemStack.EMPTY, base, inc, 0, numOperations);
		}
		
		public MachinePart(PartList part, float base, float inc, int numOperations)
		{
			this(new ItemStack(ItemRegistry.machinePart, 1, part.ordinal()), base, inc, numOperations);
		}
		
		public MachinePart(PartList part, float base, float inc)
		{
			this(part, base, inc, 0);
		}
		
		public MachinePart(PartList part, float base, float inc, float chance, int numOperations)
		{
			this(part, base, inc, numOperations);
			salvageChance = chance;
			depleted = new ItemStack(ItemRegistry.salvagePart, 1, part.getSalvageMeta());
		}
		
		public MachinePart(PartList part, ItemStack salvage, float base, float inc, float chance, int numOperations)
		{
			this(part, base, inc, numOperations);
			salvageChance = chance;
			depleted = salvage;
		}

		public void reset()
		{
			numOperations = 0;
			curBreakChance = baseBreakChance;
		}
		
		public ItemStack getSalvage()
		{
			return depleted.copy();
		}
		
		public ItemStack getItem()
		{
			return id.copy();
		}
		
		private int getActualMin()
		{
			int actualMin = minOperations;
			switch (TileEntityMachine.this.installedUpgrade)
			{
			case 1:
				actualMin *= 0.67;
				break;
			case 2:
				actualMin *= 1.5;
				break;
			case 3:
				// No effect
				break;
			case 4:
				actualMin *= 2;
				break;
			case 5:
				// No effect
			}
			return actualMin;
		}

		public void increaseChance()
		{
			// TODO: Don't increase curBreakChance if machine has the Consistency upgrade
			numOperations++;
			if (numOperations >= getActualMin()) curBreakChance *= increase;
		}
		
		public boolean shouldBreak()
		{
			if (numOperations < getActualMin()) return false;
			// TODO: Upgrades
			// Overclock = 3x normal chance
			// Underclock = 0.333x normal chance
			// Consistency = 1.5x normal chance
			switch (TileEntityMachine.this.installedUpgrade)
			{
			case 1:
				return Math.random() / 1.5 < curBreakChance;
			case 2:
				return Math.random() * 1.5 < curBreakChance;
			case 4:
				return true;
				default:
				return Math.random() < curBreakChance;
			}
			
		}
	}	
}
