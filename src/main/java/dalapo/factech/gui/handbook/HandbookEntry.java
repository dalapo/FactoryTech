package dalapo.factech.gui.handbook;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dalapo.factech.tileentity.TileEntityMachine;
import net.minecraft.item.crafting.IRecipe;

public class HandbookEntry
{
	public int pattern;
	public String title;
	public String machine;
	public List<String> pages = new ArrayList<String>();
	public List<IRecipe> recipe = new ArrayList<IRecipe>();
	
	public HandbookEntry(String t, List<String> p, List<IRecipe> r, @Nullable String mchn)
	{
		title = t;
		machine = mchn;
		pages.addAll(p);
		recipe.addAll(r);
	}
}