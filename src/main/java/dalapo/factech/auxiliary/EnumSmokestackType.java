package dalapo.factech.auxiliary;

import net.minecraft.util.IStringSerializable;

public enum EnumSmokestackType implements IStringSerializable {
	BRICKS("bricks", 0),
	SBRICKS("bricks_sand", 1),
	SSBRICKS("bricks_soulsand", 2),
	CBRICKS("bricks_charcoal", 3),
	GBRICKS("bricks_ash", 4),
	STONE("stone", 5),
	STBRICKS("stone_bricks", 6),
	IRON("iron_block", 7),
	SHEET_METAL("sheet_metal", 8);
	
	private static final EnumSmokestackType[] METADATA_LOOKUP = new EnumSmokestackType[values().length];
	
	private String name;
	private int id;
	private EnumSmokestackType(String name, int id)
	{
		this.name = name;
		this.id = id;
	}
	
	public static EnumSmokestackType getType(int meta)
	{
		return METADATA_LOOKUP[meta];
	}
	
	public int getMeta()
	{
		return id;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	static {
		for (int i=0; i<METADATA_LOOKUP.length; i++)
		{
			METADATA_LOOKUP[i] = values()[i];
		}
	}
}