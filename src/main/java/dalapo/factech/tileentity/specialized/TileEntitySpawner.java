package dalapo.factech.tileentity.specialized;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import dalapo.factech.FactoryTech;
import dalapo.factech.reference.PartList;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.TileEntityMachineNoOutput;

public class TileEntitySpawner extends TileEntityMachineNoOutput {

	public TileEntitySpawner() {
		super("spawner", 1, 4, RelativeSide.TOP);
		setDisplayName("Biosynthesizer");
		isDisabledByRedstone = true;
	}
	
	// WARNING: TERRIBLE CODE
	// EXPERIENCED MODDERS LOOK AWAY NOW
	// SERIOUSLY THIS IS SOMETHING NOTCH WOULD WRITE
	private EntityLiving getSpawn(World world, ItemStack itemIn)
	{
		Item item = itemIn.getItem();
		int dmg = itemIn.getItemDamage();
		if (item == Items.ROTTEN_FLESH)
		{
			return new EntityZombie(world);
		}
		if (item == Items.BONE)
		{
			return new EntitySkeleton(world);
		}
		if (item == Items.SPIDER_EYE)
		{
			return new EntitySpider(world);
		}
		if (item == Items.GUNPOWDER)
		{
			return new EntityCreeper(world);
		}
		if (item == Items.PORKCHOP)
		{
			return new EntityPig(world);
		}
		if (item == Items.BEEF)
		{
			return new EntityCow(world);
		}
		if (item == Items.CHICKEN)
		{
			return new EntityChicken(world);
		}
		if (item == Items.MUTTON)
		{
			return new EntitySheep(world);
		}
		if (item == Items.SLIME_BALL)
		{
			return new EntitySlime(world);
		}
		if (item == Items.BLAZE_ROD)
		{
			return new EntityBlaze(world);
		}
		if (item == Items.ENDER_PEARL)
		{
			return new EntityEnderman(world);
		}
		if (item == Items.EMERALD)
		{
			return new EntityVillager(world);
		}
		return null;
	}
	
	@Override
	public boolean canRun()
	{
		return super.canRun() && hasWork;
	}
	
	@Override
	public void getHasWork()
	{
		hasWork = getSpawn(world, getInput()) != null && world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}
	
	@Override
	protected void fillMachineParts() {
		partsNeeded[0] = new MachinePart(PartList.CORE, 0.1F, 1.15F, 0.8F*kValue[0][1], (int)(6*kValue[0][0]));
		partsNeeded[1] = new MachinePart(PartList.CIRCUIT_0, 0.15F, 1.1F, 0.5F*kValue[1][1], (int)(5*kValue[1][0]));
		partsNeeded[2] = new MachinePart(PartList.BATTERY, 0.1F, 1.1F, 0.8F*kValue[2][1], 0);
		partsNeeded[3] = new MachinePart(PartList.MOTOR, 0.2F, 1.2F, 0.5F*kValue[3][1], (int)(4*kValue[3][0]));
	}

	@Override
	protected boolean performAction() {
		EntityLiving toSpawn = getSpawn(world, getInput(0));
		if (toSpawn == null) return false;
		toSpawn.setPosition(pos.getX()+0.5,  pos.getY()-2.5, pos.getZ()+0.5);
		world.spawnEntity(toSpawn);
		if (FactoryTech.random.nextInt(4) == 0) getInput().shrink(1);
		return true;
	}

	@Override
	public int getOpTime() {
		// TODO Auto-generated method stub
		return 200;
	}

}
