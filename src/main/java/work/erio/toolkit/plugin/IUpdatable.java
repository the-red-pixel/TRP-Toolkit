package work.erio.toolkit.plugin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import work.erio.toolkit.tile.TileEntityUniversalSign;

public interface IUpdatable {
    void onNeighborUpdate(TileEntityUniversalSign te, World worldIn, BlockPos targetPos, BlockPos currentPos);

    void onNeighborTileUpdate(TileEntityUniversalSign te, IBlockAccess worldIn, BlockPos targetPos, BlockPos currentPos);
}
