package dalapo.factech.item;

// Jetpack. Consumes Energy Cores.
// Constantly accelerates player. (Accounts for stuff in inventory?)
// Should probably add some sort of Core storage mechanism to account for them stacking to 4

// Why is fall damage based on time in air instead of normal velocity?
public class ItemJetpack extends ItemBase
{
	public ItemJetpack(String name)
	{
		super(name);
		setMaxStackSize(1);
	}

}
