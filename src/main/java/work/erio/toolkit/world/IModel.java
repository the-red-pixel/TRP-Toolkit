package work.erio.toolkit.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IModel {
    IBlockState getBlockState(BlockPos pos);

    boolean setBlockState(BlockPos pos, IBlockState blockState);

    TileEntity getTileEntity(BlockPos pos);

    List<TileEntity> getTileEntities();

    void setTileEntity(BlockPos pos, TileEntity tileEntity);

    void removeTileEntity(BlockPos pos);
}
