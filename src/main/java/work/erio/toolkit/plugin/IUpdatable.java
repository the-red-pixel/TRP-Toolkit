package work.erio.toolkit.plugin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IUpdatable {
    void onNeighborUpdate(World worldIn, BlockPos targetPos, BlockPos currentPos);

    void onNeighborTileUpdate(IBlockAccess worldIn, BlockPos targetPos, BlockPos currentPos);
}
