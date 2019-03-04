package work.erio.toolkit.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model implements IModel {
    private final int SIZE = 16;
    private int[][][] blocks;
    private byte[][][] metadata;
    private List<TileEntity> tileEntities;

    public Model() {
        this.blocks = new int[SIZE][SIZE][SIZE];
        this.metadata = new byte[SIZE][SIZE][SIZE];
        this.tileEntities = new ArrayList<>();
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int id = this.blocks[x][y][z];
        byte meta = this.metadata[x][y][z];
        Block block = Block.REGISTRY.getObjectById(id);
        return block.getStateFromMeta(meta);
    }

    @Override
    public IBlockState getBlockState(int x, int y, int z) {
        int id = this.blocks[x][y][z];
        byte meta = this.metadata[x][y][z];
        Block block = Block.REGISTRY.getObjectById(id);
        return block.getStateFromMeta(meta);
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState blockState) {
        Block block = blockState.getBlock();
        int id = Block.REGISTRY.getIDForObject(block);
        if (id == -1) {
            return false;
        }
        int meta = block.getMetaFromState(blockState);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        this.blocks[x][y][z] = (short) id;
        this.metadata[x][y][z] = (byte) meta;
        return true;
    }

    @Override
    public boolean setBlockState(int x, int y, int z, IBlockState blockState) {
        Block block = blockState.getBlock();
        int id = Block.REGISTRY.getIDForObject(block);
        if (id == -1) {
            return false;
        }
        int meta = block.getMetaFromState(blockState);
        this.blocks[x][y][z] = (short) id;
        this.metadata[x][y][z] = (byte) meta;
        return true;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return this.tileEntities.parallelStream().filter(te -> te.getPos().equals(pos)).findFirst().orElse(null);
    }

    @Override
    public List<TileEntity> getTileEntities() {
        return this.tileEntities;
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntity) {
        removeTileEntity(pos);
        if (tileEntity == null) {
            return;
        }
        this.tileEntities.add(tileEntity);
    }

    @Override
    public void removeTileEntity(BlockPos pos) {
        this.tileEntities.removeIf(te -> te.getPos().equals(pos));
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
