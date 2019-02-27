package work.erio.toolkit.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.gui.GuiBlockBox;
import work.erio.toolkit.gui.GuiBlockJS;
import work.erio.toolkit.gui.GuiBlockPulse;
import work.erio.toolkit.tile.TileEntityBox;
import work.erio.toolkit.tile.TileEntityJS;
import work.erio.toolkit.tile.TileEntityKeypad;
import work.erio.toolkit.tile.TileEntityPulse;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/20.
 */
public class GuiHandler implements IGuiHandler {

    public static final int GUI_BLOCK_BOX = 1;
    public static final int GUI_BLOCK_JS = 2;
    public static final int GUI_BLOCK_PULSE = 3;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        BlockPos pos = new BlockPos(x, y, z);
//        TileEntity tileEntity = world.getTileEntity(pos);
//        if (tileEntity instanceof TileEntityBox) {
//            TileEntityBox te = (TileEntityBox) tileEntity;
//            return new GuiBlockBox(te);
//        } else if (tileEntity instanceof TileEntityKeypad) {
//            TileEntityJS te = (TileEntityJS) tileEntity;
//            return new GuiBlockJS(te);
//        } else if (tileEntity instanceof TileEntityPulse) {
//            TileEntityPulse te = (TileEntityPulse) tileEntity;
//            return new GuiBlockPulse(te);
//        }

//        BlockPos pos = new BlockPos(x, y, z);
//        TileEntity tileEntity = world.getTileEntity(pos);
//        switch (ID) {
//            case GUI_BLOCK_BOX:
//                return new GuiBlockBox((TileEntityBox) tileEntity);
//            case GUI_BLOCK_JS:
//                return new GuiBlockJS((TileEntityJS) tileEntity);
//            case GUI_BLOCK_PULSE:
//                return new GuiBlockPulse((TileEntityPulse) tileEntity);
//        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
//        BlockPos pos = new BlockPos(x, y, z);
//        TileEntity tileEntity = world.getTileEntity(pos);
//        switch (ID) {
//            case GUI_BLOCK_BOX:
//                return new GuiBlockBox((TileEntityBox) tileEntity);
//            case GUI_BLOCK_JS:
//                return new GuiBlockJS((TileEntityJS) tileEntity);
//            case GUI_BLOCK_PULSE:
//                return new GuiBlockPulse((TileEntityPulse) tileEntity);
//        }
        return null;
    }
}
