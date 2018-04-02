package dalapo.factech.auxiliary;

import net.minecraft.util.IStringSerializable;

public enum EnumOreType implements IStringSerializable {
	COPPER("copper", 0),
	NICKEL("nickel", 1);

	private static final EnumOreType[] METADATA_LOOKUP = new EnumOreType[values().length];
	String name;
	int id;
	private EnumOreType(String str, int id)
	{
		name = str;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	public int getMeta()
	{
		return id;
	}
	
	public static EnumOreType getType(int id)
	{
		return METADATA_LOOKUP[id % values().length];
	}
	
	static {
		for (int i=0; i<METADATA_LOOKUP.length; i++)
		{
			METADATA_LOOKUP[i] = values()[i];
		}
	}
}