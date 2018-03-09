package work.erio.toolkit.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import work.erio.toolkit.gui.GuiBlockBox;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class TileEntityBox extends TileEntity {


    public void showGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBlockBox(this));
    }


}
