package dalapo.factech.gui;

import dalapo.factech.gui.handbook.GuiHandbook;
import dalapo.factech.gui.widget.WidgetToggleSwitch;
import dalapo.factech.helper.MachineContainerFactory;
import dalapo.factech.tileentity.TileEntityBasicInventory;
import dalapo.factech.tileentity.TileEntityFluidMachine;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.automation.TileEntityBufferCrate;
import dalapo.factech.tileentity.automation.TileEntityCrate;
import dalapo.factech.tileentity.automation.TileEntityFilterMover;
import dalapo.factech.tileentity.automation.TileEntityInventorySensor;
import dalapo.factech.tileentity.automation.TileEntityItemPusher;
import dalapo.factech.tileentity.automation.TileEntityItemRedis;
import dalapo.factech.tileentity.automation.TileEntityMechArm;
import dalapo.factech.tileentity.automation.TileEntitySequencePlacer;
import dalapo.factech.tileentity.specialized.TileEntityAutoCrafter;
import dalapo.factech.tileentity.specialized.TileEntityCircuitScribe;
import dalapo.factech.tileentity.specialized.TileEntityCrucible;
import dalapo.factech.tileentity.specialized.TileEntityEnergizer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FacTechGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0)
		{
			TileEntityMachine te = (TileEntityMachine)world.getTileEntity(new BlockPos(x, y, z));
			return MachineContainerFactory.getContainer(te, player.inventory, te.getName());
//			return new ContainerBasicMachine(te.countPartSlots(), te, player.inventory, 35, 35, 89, 35);
		}
		else if (ID == 1 || ID == 12 || ID == 13)
		{
			TileEntityBasicInventory te = (TileEntityBasicInventory)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(3, 3, te, player.inventory);
		}
		else if (ID == 2)
		{
			TileEntityAutoCrafter te = (TileEntityAutoCrafter)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerAutoCrafter(te, player);
		}
		else if (ID == 3)
		{
			TileEntityCrucible te = (TileEntityCrucible)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerCrucible(te, player.inventory);
		}
		else if (ID == 5)
		{
			TileEntityMechArm te = (TileEntityMechArm)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(1, 1, te, player.inventory);
		}
		else if (ID == 7)
		{
			TileEntityCrate te = (TileEntityCrate)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(2, 9, te, player.inventory);
		}
		else if (ID == 8)
		{
			TileEntityEnergizer te = (TileEntityEnergizer)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(1, 1, te, player.inventory);
		}
		else if (ID == 9)
		{
			TileEntitySequencePlacer te = (TileEntitySequencePlacer)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(2, 9, te, player.inventory);
		}
		else if (ID == 10)
		{
			TileEntityBufferCrate te = (TileEntityBufferCrate)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(2, 9, te, player.inventory);
		}
		else if (ID == 11)
		{
			TileEntityInventorySensor te = (TileEntityInventorySensor)world.getTileEntity(new BlockPos(x, y, z));
			return new ContainerBasicInventory(3, 3, te, player.inventory);
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 0)
		{
			TileEntityMachine te = (TileEntityMachine)world.getTileEntity(new BlockPos(x, y, z));
			if (te != null) return MachineContainerFactory.getGui(te, player.inventory, te.getContainerName());
//			return new GuiBasicMachine(new ContainerBasicMachine(te.countPartSlots(), te, player.inventory, 35, 35, 89, 35), player.inventory, te);
		}
		else if (ID == 1)
		{
			TileEntityBasicInventory te = (TileEntityBasicInventory)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(3, 3, te, player.inventory), player.inventory, "stackfilter", te);
		}
		else if (ID == 2)
		{
			TileEntityAutoCrafter te = (TileEntityAutoCrafter)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerAutoCrafter(te, player), player.inventory, "autocrafter", te);
		}
		else if (ID == 3)
		{
			TileEntityFluidMachine te = (TileEntityFluidMachine)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiFluidMachine(new ContainerCrucible(te, player.inventory), player.inventory, te);
		}
		else if (ID == 4)
		{
			TileEntityItemRedis te = (TileEntityItemRedis)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiItemRedis("itemredis", te);
		}
		else if (ID == 5)
		{
			TileEntityMechArm te = (TileEntityMechArm)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(1, 1, te, player.inventory), player.inventory, "energizer_gui", te);
		}
		else if (ID == 6)
		{
			return new GuiHandbook(player.getHeldItemMainhand());
		}
		else if (ID == 7)
		{
			TileEntityCrate te = (TileEntityCrate)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(2, 9, te, player.inventory), player.inventory, "crate_gui", te);
		}
		else if (ID == 8)
		{
			TileEntityEnergizer te = (TileEntityEnergizer)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(1, 1, te, player.inventory), player.inventory, "energizer_gui", te);
		}
		else if (ID == 9)
		{
			TileEntitySequencePlacer te = (TileEntitySequencePlacer)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(2, 9, te, player.inventory), player.inventory, "sequencedropper", te);
		}
		else if (ID == 10)
		{
			TileEntityBufferCrate te = (TileEntityBufferCrate)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiBasicInventory(new ContainerBasicInventory(2, 9, te, player.inventory), player.inventory, "crate_gui", te);
		}
		else if (ID == 11)
		{
			TileEntityInventorySensor te = (TileEntityInventorySensor)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiInventorySensor(new ContainerBasicInventory(3, 3, te, player.inventory), player.inventory, "stackfilter", te);
		}
		else if (ID == 12)
		{
			TileEntityFilterMover te = (TileEntityFilterMover)world.getTileEntity(new BlockPos(x, y, z));
			GuiBasicInventory inv = new GuiBasicInventory(new ContainerBasicInventory(3, 3, te, player.inventory), player.inventory, "stackfilter", te);
			inv.addWidget(new WidgetToggleSwitch(inv, 0, 136, 20, "Round Robin", "First Match"));
			return inv;
		}
		else if (ID == 13)
		{
			TileEntityItemPusher te = (TileEntityItemPusher)world.getTileEntity(new BlockPos(x, y, z));
			return new GuiPulsePiston(new ContainerBasicInventory(3, 3, te, player.inventory), player.inventory, "stackfilter", te);
		}
		return null;
	}
}