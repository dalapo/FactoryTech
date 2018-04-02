package dalapo.factech.gui;

import dalapo.factech.helper.FacMiscHelper;
import dalapo.factech.tileentity.specialized.TileEntityAutoCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ContainerAutoCrafter extends ContainerBasicInventory {
	
	private TileEntityAutoCrafter te;
	private World worldObj;
	private InventoryCrafting matrix;
	
	public ContainerAutoCrafter(TileEntityAutoCrafter te, EntityPlayer ep)
	{
		super(3, 3, te, ep.inventory);
		this.addSlotToContainer(new Slot(te, 9, 152, 8));
		this.addSlotToContainer(new Slot(te, 10, 152, 35));
		this.worldObj = te.getWorld(); // Should be safe since the constructor is called server-side
		this.matrix = new InventoryCrafting(this, 3, 3);
		te.updateValues(this.matrix);
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clicktype, EntityPlayer ep)
	{
//		Logger.info(te.getStackInSlot(slotId));
		return super.slotClick(slotId, dragType, clicktype, ep);
	}
	@Override
	public void onCraftMatrixChanged(IInventory handler)
	{
		super.onCraftMatrixChanged(handler);
		// More stuff if necessary
	}
	
	public ContainerAutoCrafter(TileEntityAutoCrafter te, World world)
	{
		this(te, FacMiscHelper.getFakePlayer(world));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
    {
        InventoryCrafting mtrx = new InventoryCrafting(this, 3, 3);
        IRecipe result = CraftingManager.findMatchingRecipe(mtrx, worldObj);
        if (result != null && !result.getRecipeOutput().isEmpty())
        {
        	te.updateValues(mtrx);
        }
        super.onContainerClosed(playerIn);
    }
}