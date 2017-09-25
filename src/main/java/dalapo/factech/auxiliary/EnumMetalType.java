package dalapo.factech.auxiliary;

import net.minecraft.util.IStringSerializable;

public enum EnumMetalType implements IStringSerializable
{
	DIAMOND("diamond", 0),
	RIVETED("rivet", 1),
	SMOOTH("smooth", 2),
	RUST("rust", 3),
	SIDING("siding", 4),
	CAUTION("caution", 5);
	
	private static EnumMetalType[] METADATA_LOOKUP = new EnumMetalType[values().length];
	private String name;
	private int id;
	private EnumMetalType(String name, int id)
	{
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public int getMeta()
	{
		return id;
	}
	
	public static EnumMetalType getType(int id)
	{
		return METADATA_LOOKUP[id];
	}
	
	static {
		for (int i=0; i<values().length; i++)
		{
			METADATA_LOOKUP[i] = values()[i];
		}
	}
}
