package work.erio.toolkit.plugin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PluginBlockName extends AbstractPlugin implements IUpdatable {
    public PluginBlockName() {
        setName("Test");
        setDescription("...");
    }

    @Override
    public void onNeighborUpdate(World worldIn, BlockPos targetPos) {

    }

    @Override
    public void onNeighborTileUpdate(IBlockAccess worldIn, BlockPos targetPos) {

    }
}
