package dalapo.factech.reference;

import net.minecraft.util.DamageSource;

public class DamageSourceList {
	private DamageSourceList() {}
	
	public static DamageSource machine = new DamageSource("machine");
	public static DamageSource acid = new DamageSource("acid");
	public static DamageSource acidSpray = new DamageSource("acidspray");
	public static DamageSource tesla = new DamageSource("tesla");
	
	static {
		tesla.setDamageBypassesArmor();
	}
}
