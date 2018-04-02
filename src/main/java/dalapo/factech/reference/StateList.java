package dalapo.factech.reference;

import dalapo.factech.auxiliary.EnumBrickType;
import dalapo.factech.auxiliary.EnumMetalType;
import dalapo.factech.auxiliary.EnumOreBlockType;
import dalapo.factech.auxiliary.EnumOreType;
import dalapo.factech.auxiliary.EnumSmokestackType;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.util.EnumFacing;

public class StateList {
	private StateList() {}
	
	public static final PropertyEnum<EnumFacing> directions = PropertyEnum.create("facing", EnumFacing.class);
	public static final PropertyEnum<EnumOreType> oretype = PropertyEnum.create("oretype", EnumOreType.class);
	public static final PropertyEnum<EnumBrickType> bricktype = PropertyEnum.create("bricktype", EnumBrickType.class);
	public static final PropertyEnum<EnumMetalType> metaltype = PropertyEnum.create("metaltype", EnumMetalType.class);
	public static final PropertyEnum<EnumOreBlockType> oreblocktype = PropertyEnum.create("oreblocktype", EnumOreBlockType.class);
	public static final PropertyEnum<EnumSmokestackType> smokestacktype = PropertyEnum.create("smokestacktype", EnumSmokestackType.class);
}