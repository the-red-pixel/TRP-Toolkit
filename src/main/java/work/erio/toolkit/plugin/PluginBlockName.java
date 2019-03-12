package work.erio.toolkit.plugin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import work.erio.toolkit.tile.TileEntityUniversalSign;

public class PluginBlockName extends AbstractPlugin implements IUpdatable {
    public PluginBlockName() {
        setName("BlockName");
        setDescription("...");
    }

    @Override
    public void onNeighborUpdate(TileEntityUniversalSign te, World worldIn, BlockPos targetPos, BlockPos currentPos) {
        te.setSignText(0, worldIn.getBlockState(targetPos).getBlock().getLocalizedName());
    }

    @Override
    public void onNeighborTileUpdate(TileEntityUniversalSign te, IBlockAccess worldIn, BlockPos targetPos, BlockPos currentPos) {

    }
}
