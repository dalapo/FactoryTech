package dalapo.factech.tileentity.automation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import dalapo.factech.tileentity.TileEntityBasicInventory;

public class TileEntityCrate extends TileEntityBasicInventory
{
	public TileEntityCrate() {
		super("crate", 18);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("Crate");
	}
	
	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
