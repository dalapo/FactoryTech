package dalapo.factech.debug;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.item.ItemStack;

public class ASMConstructorStatement
{
	private static final Map<String, String> ClassNameMap = new HashMap<String, String>();
	private static final Map<String, Transformer> transformers = new HashMap<String, Transformer>();
	private static final String ITEMSTACK_STRING = "net/minecraft/item/ItemStack";
	
	static {
		ClassNameMap.put(ITEMSTACK_STRING, "aip");
		
		
	}
	
	public static void messWithItemStackConstructor()
	{
		
	}
	
	private static byte[] transformItemStackConstructor(byte[] classIn)
	{
		return classIn;
	}
	
	
	private static interface Transformer extends Function<byte[], byte[]> {}
}