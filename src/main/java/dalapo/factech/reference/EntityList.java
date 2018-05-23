package dalapo.factech.reference;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.BiMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;

public class EntityList
{
	private EntityList() {}
	
	public static Map<String, Class<? extends Entity>> list = new HashMap<>();
	
	static {
		list.put("EntityZombie", EntityZombie.class);
		list.put("EntitySkeleton", EntitySkeleton.class);
		list.put("EntitySpider", EntitySpider.class);
		list.put("EntityCaveSpider", EntityCaveSpider.class);
		list.put("EntityCreeper", EntityCreeper.class);
		list.put("EntityBlaze", EntityBlaze.class);
		list.put("EntityEnderman", EntityEnderman.class);
		list.put("EntitySlime", EntitySlime.class);
	}
}