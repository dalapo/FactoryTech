package dalapo.factech.auxiliary;

import net.minecraft.util.IStringSerializable;

public enum EnumOreBlockType implements IStringSerializable {
	COPPER("copper", 0),
	NICKEL("nickel", 1),
	CUPRONICKEL("cupronickel", 2),
	INVAR("invar", 3);
	
	private static final EnumOreBlockType[] METADATA_LOOKUP = new EnumOreBlockType[values().length];
	
	private String name;
	private int id;
	private EnumOreBlockType(String str, int id)
	{
		name = str;
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public static EnumOreBlockType getType(int id)
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
