package work.erio.toolkit.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkModel extends Chunk {
    private World world;

    public ChunkModel(World worldIn, int x, int z) {
        super(worldIn, x, z);
        this.world = worldIn;
    }

    @Override
    protected void generateHeightMap() {
    }

    @Override
    public void generateSkylightMap() {
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return this.world.getBlockState(pos);
    }

    @Override
    public boolean isEmptyBetween(int startY, int endY) {
        return false;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType createEntityType) {
        return this.world.getTileEntity(pos);
    }
}
