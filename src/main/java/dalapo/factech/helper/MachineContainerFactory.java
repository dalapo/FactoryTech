package dalapo.factech.helper;

import java.util.ArrayList;
import java.util.List;

import dalapo.factech.gui.ContainerBasicMachine;
import dalapo.factech.gui.GuiAgitator;
import dalapo.factech.gui.GuiBasicMachine;
import dalapo.factech.gui.GuiCircuitScribe;
import dalapo.factech.gui.GuiFacInventory;
import dalapo.factech.gui.GuiFluidDrill;
import dalapo.factech.gui.GuiFluidMachine;
import dalapo.factech.gui.GuiSaw;
import dalapo.factech.gui.GuiSluice;
import dalapo.factech.gui.GuiStabilizer;
import dalapo.factech.gui.GuiTemperer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dalapo.factech.tileentity.TileEntityMachine;
import dalapo.factech.tileentity.specialized.TileEntityPropaneFurnace;

// also i cannot promise this code being good :(
public class MachineContainerFactory {
	private MachineContainerFactory() {}
	
//	@SuppressWarnings("unchecked")
	public static ContainerBasicMachine getContainer(TileEntityMachine te, IInventory playerInv, String id)
	{
		List<Pair<Integer, Integer>> input = new ArrayList<>();
		List<Pair<Integer, Integer>> output = new ArrayList<>();
		int playerOffset = 0;
		switch (id)
		{
		case "centrifuge":
		case "magcent":
//			return new ContainerBasicMachine(3, te, playerInv, new Pair[] {new Pair(35, 35)}, new Pair[] {new Pair(89, 17), new Pair(89, 35), new Pair(89, 53)});
			input.add(new Pair<Integer, Integer>(35, 35));
			output.add(new Pair<Integer, Integer>(89, 17));
			output.add(new Pair<Integer, Integer>(89, 35));
			output.add(new Pair<Integer, Integer>(89, 53));
			break;
		case "crucible":
			input.add(new Pair<Integer, Integer>(80, 44));
			break;
		case "woodcutter":
			output.add(new Pair<Integer, Integer>(80, 35));
			break;
		case "fluiddrill":
		case "disruptor":
		case "miner":
		case "disassembler":
		case "teslacoil":
		case "iondisperser":
		case "deepdrill":
			// No IO
			break;
		case "sluice":
			for (int i=0; i<3; i++)
			{
				for (int j=0; j<3; j++)
				{
					output.add(new Pair<Integer, Integer>(71+(i*18), 17+(j*18)));
				}
			}
//			output.add(new Pair<Integer, Integer>(89, 17));
//			output.add(new Pair<Integer, Integer>(89, 35));
//			output.add(new Pair<Integer, Integer>(89, 53));
//			output.add(new Pair<Integer, Integer>(107, 35));
			break;
		case "fridge":
//			input.add(new Pair<Integer, Integer>(999, 999));
			output.add(new Pair<Integer, Integer>(98, 38));
			break;
		case "drill":
			input.add(new Pair<Integer, Integer>(80, 18));
			output.add(new Pair<Integer, Integer>(80, 55));
			break;
		case "agitator":
			input.add(new Pair<Integer, Integer>(52, 47));
			output.add(new Pair<Integer, Integer>(108, 47));
			break;
		case "cchamber":
			input.add(new Pair<Integer, Integer>(90, 8));
			output.add(new Pair<Integer, Integer>(90, 60));
			break;
		case "electroplater":
			input.add(new Pair<Integer, Integer>(62, 38));
			output.add(new Pair<Integer, Integer>(116, 38));
			break;
		case "potionmixer":
			input.add(new Pair<Integer, Integer>(35, 44));
			input.add(new Pair<Integer, Integer>(27, 61));
			input.add(new Pair<Integer, Integer>(43, 61));
			output.add(new Pair<Integer, Integer>(89, 35));
			break;
		case "propanefurnace":
			input.add(new Pair<Integer, Integer>(98, 24));
			input.add(new Pair<Integer, Integer>(35, 53));
			output.add(new Pair<Integer, Integer>(98, 60));
			output.add(new Pair<Integer, Integer>(62, 65));
			break;
		case "circuitscribe":
			input.add(new Pair<Integer, Integer>(9, 29));
			output.add(new Pair<Integer, Integer>(9, 71));
			playerOffset = 34;
			break;
		case "stabilizer":
			input.add(new Pair<Integer, Integer>(80, 35));
			output.add(new Pair<Integer, Integer>(134, 35));
			break;
		case "corecharger":
		case "spawner":
		case "planter":
			input.add(new Pair<Integer, Integer>(80, 35));
			break;
		case "temperer":
			input.add(new Pair<Integer, Integer>(35, 35));
			output.add(new Pair<Integer, Integer>(116, 35));
			break;
		default:
			input.add(new Pair<Integer, Integer>(35, 35));
			output.add(new Pair<Integer, Integer>(89, 35));
			break;
		}
		return new ContainerBasicMachine(te.countPartSlots(), playerOffset, te, playerInv, input.toArray(new Pair[] {}), output.toArray(new Pair[] {}));

	//	return new ContainerBasicMachine(te.countPartSlots(), te, playerInv, input.toArray(new Pair[] {}), output.toArray(new Pair[] {}));
	}
	
	@SideOnly(Side.CLIENT)
	public static GuiFacInventory getGui(TileEntityMachine te, IInventory playerInv, String id)
	{
		switch (id)
		{
		case "sluice":
			return new GuiSluice(getContainer(te, playerInv, id), playerInv, te);
		case "circuitscribe":
			return new GuiCircuitScribe(getContainer(te, playerInv, id), playerInv, te);
		case "crucible":
			GuiFluidMachine crucible = new GuiFluidMachine(getContainer(te, playerInv, id), playerInv, "crucible_gui", te) {
				protected void drawProgressBar()
				{
					int progress = te.getProgressScaled(33);
					this.drawTexturedModalRect(guiLeft + 32, guiTop + 64 - progress, 198, 33 - progress, 4, progress);
				}
			};
			crucible.setCoords(72, 61);
			crucible.setSize(32, 2);
			return crucible;
		case "agitator":
			return new GuiAgitator(getContainer(te, playerInv, id), playerInv, te);
		case "cchamber":
			return new GuiFluidMachine(getContainer(te, playerInv, id), playerInv, "cchamber_gui", te) {
				@Override
				protected void drawProgressBar()
				{
					int progress = te.getProgressScaled(53);
					this.drawTexturedModalRect(guiLeft + 50, guiTop + 27, 197, 0, progress, 27);
				}
			};
		case "stabilizer":
			return new GuiStabilizer(getContainer(te, playerInv, id), playerInv, te);
		case "temperer":
			return new GuiTemperer(getContainer(te, playerInv, id), playerInv, te);
		case "saw":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "saw_gui", te) {
				@Override
				protected void drawProgressBar()
				{
					int progress = te.getProgressScaled(21);
//					this.zLevel = 100;
					this.drawTexturedModalRect(guiLeft + 34, guiTop + 16 + progress, 176, 137, 18, 18);
				}
			};
		case "drill":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "drill_gui", te) {
				@Override
				protected void drawProgressBar()
				{
					int progress = te.getProgressScaled(49);
					this.drawTexturedModalRect(guiLeft + 66, guiTop + 19, 216, 50, 7, progress);
					this.drawTexturedModalRect(guiLeft + 103, guiTop + 19, 216, 50, 7, progress);
				}
			};
		case "fluiddrill":
			return new GuiFluidDrill(getContainer(te, playerInv, id), playerInv, te);
		case "potionmixer":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "potionmixer_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					int progress = te.getProgressScaled(46);
					this.drawTexturedModalRect(guiLeft + 22, guiTop + 78 - progress, 212, 65 - progress, 42, progress);
				}
			};
		case "miner":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "autominer_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					if (te.age > 0)
					{
						int progress = te.getProgressScaled(50);
						if (progress < 18)
						{
							this.drawTexturedModalRect(guiLeft + 58, guiTop + 46 - progress, 200, 2, 6, 7);
						}
						else
						{
							this.drawTexturedModalRect(guiLeft + 39 + progress, guiTop + 29, 200, 2, 6, 7);
						}
					}
				}
			};
		case "centrifuge":
		case "magcent":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "centrifuge_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					int progress = te.getProgressScaled(33);
					this.drawTexturedModalRect(guiLeft + 54, guiTop + 18, 197, 0, progress, 49);
				}
			};
		case "fridge":
//			return new GuiFluidMachine(getContainer(te, playerInv, id), playerInv, te);
			
			return new GuiFluidMachine(getContainer(te, playerInv, id), playerInv, "fridge_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					int progress = te.getProgressScaled(44);
					this.drawTexturedModalRect(guiLeft + 50, guiTop + 32, 197, 0, progress, 27);
				}
			};
		case "propanefurnace":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "propfurnace_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					int progress = te.getProgressScaled(13);
					int propane = 5 * ((TileEntityPropaneFurnace)te).getPropane();
					this.drawTexturedModalRect(guiLeft + 99, guiTop + 44, 185, 47, 13, progress);
					this.drawTexturedModalRect(guiLeft + 15, guiTop + 81 - propane, 176, 47, 9, propane);
				}
			};
		case "electroplater":
			return new GuiFluidMachine(getContainer(te, playerInv, id), playerInv, "electroplater_gui", te)
			{
				@Override
				public void drawProgressBar()
				{
					int progress = te.getProgressScaled(30);
					this.drawTexturedModalRect(guiLeft + 82, guiTop + 41, 176, 0, progress, 8);
				}
			};
		case "disruptor":
		case "disassembler":
		case "teslacoil":
		case "iondisperser":
		case "deepdrill":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "gui_blank", te);
		case "woodcutter":
		case "corecharger":
		case "spawner":
		case "planter":
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, "gui_single_slot", te);
		default:
//			Logger.info("Using default GUI; ID = " + id);
			return new GuiBasicMachine(getContainer(te, playerInv, id), playerInv, te).setBarCoords(60, 37);
		}
	}
}