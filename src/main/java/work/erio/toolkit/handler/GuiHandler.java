package work.erio.toolkit.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import work.erio.toolkit.gui.GuiBlockBox;
import work.erio.toolkit.tile.TileEntityBox;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/20.
 */
public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        /*
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityBox) {
            TileEntityBox te = (TileEntityBox) tileEntity;
            return new GuiBlockBox(te);
        }
        */
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        /*
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityBox) {
            TileEntityBox te = (TileEntityBox) tileEntity;
            return new GuiBlockBox(te);
        }
        */
        return null;
    }
}
