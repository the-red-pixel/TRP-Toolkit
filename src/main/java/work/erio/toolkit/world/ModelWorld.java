package work.erio.toolkit.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModelWorld extends WorldClient {
    private static final WorldSettings WORLD_SETTINGS = new WorldSettings(0, GameType.CREATIVE, false, false, WorldType.FLAT);
    private IModel model;

    public ModelWorld(IModel model) {
        super(null, WORLD_SETTINGS, 0, EnumDifficulty.PEACEFUL, Minecraft.getMinecraft().mcProfiler);
        this.model = model;

        for (final TileEntity tileEntity : model.getTileEntities()) {
            initializeTileEntity(tileEntity);
        }
    }

    private void initializeTileEntity(TileEntity tileEntity) {
        tileEntity.setWorld(this);
        tileEntity.getBlockType();
        try {
            tileEntity.invalidate();
            tileEntity.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return this.model.getBlockState(pos);
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state, int flags) {
        return this.model.setBlockState(pos, state);
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return this.model.getTileEntity(pos);
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntity) {
        this.model.setTileEntity(pos, tileEntity);
        initializeTileEntity(tileEntity);
    }

    @Override
    public void removeTileEntity(BlockPos pos) {
        this.model.removeTileEntity(pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
        return 15;
    }

    @Override
    public float getLightBrightness(BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
        return getBlockState(pos).isNormalCube();
    }

    @Override
    public void calculateInitialSkylight() {
    }

    @Override
    protected void calculateInitialWeather() {
    }

    @Override
    public void setSpawnPoint(BlockPos pos) {
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        final IBlockState blockState = getBlockState(pos);
        return blockState.getBlock().isAir(blockState, this, pos);
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return Biomes.ICE_MOUNTAINS;
    }

    public int getWidth() {
        return this.model.getSize();
    }

    public int getLength() {
        return this.model.getSize();
    }

    @Override
    public int getHeight() {
        return this.model.getSize();
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        this.chunkProvider = new ChunkProviderModel(this);
        return this.chunkProvider;
    }

    @Override
    public Entity getEntityByID(int id) {
        return null;
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side) {
        return isSideSolid(pos, side, false);
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return getBlockState(pos).isSideSolid(this, pos, side);
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    public List<TileEntity> getTileEntities() {
        return this.model.getTileEntities();
    }

    public IModel getModel() {
        return this.model;
    }

}
