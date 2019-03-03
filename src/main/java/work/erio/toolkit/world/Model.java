package work.erio.toolkit.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class Model implements IModel {
    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return null;
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState blockState) {
        return false;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }

    @Override
    public List<TileEntity> getTileEntities() {
        return null;
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntity) {

    }

    @Override
    public void removeTileEntity(BlockPos pos) {

    }
}
