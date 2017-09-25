package dalapo.factech.auxiliary;

import net.minecraft.util.IStringSerializable;

public enum EnumBrickType implements IStringSerializable {
	SAND("sand", 0),
	SOULSAND("soulsand", 1),
	CHARCOAL("charcoal", 2),
	CLAY("clay", 3);
	
	private static final EnumBrickType[] METADATA_LOOKUP = new EnumBrickType[values().length];
	
	private String name;
	private int id;
	private EnumBrickType(String str, int id)
	{
		name = str;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public static EnumBrickType getType(int id)
	{
		return METADATA_LOOKUP[id % values().length];
	}
	
	public int getMeta()
	{
		return id;
	}
	
	static {
		for (int i=0; i<METADATA_LOOKUP.length; i++)
		{
			METADATA_LOOKUP[i] = values()[i];
		}
	}
}
