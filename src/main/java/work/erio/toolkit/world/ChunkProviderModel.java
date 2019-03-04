package work.erio.toolkit.world;

import com.google.common.base.MoreObjects;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkProviderModel extends ChunkProviderClient implements IChunkProvider {
    private final Map<Long, ChunkModel> chunks = new ConcurrentHashMap<>();
    private ModelWorld world;
    private Chunk emptyChunk;

    public ChunkProviderModel(ModelWorld world) {
        super(world);
        this.world = world;
        this.emptyChunk = new EmptyChunk(world, 0, 0) {
            @Override
            public boolean isEmpty() {
                return false;
            }
        };
    }

    private boolean chunkExists(final int x, final int z) {
        return x >= 0 && z >= 0 && x < this.world.getWidth() && z < this.world.getLength();
    }

    @Override
    public Chunk getLoadedChunk(final int x, final int z) {
        if (!chunkExists(x, z)) {
            return this.emptyChunk;
        }

        final long key = ChunkPos.asLong(x, z);

        ChunkModel chunk = this.chunks.get(key);
        if (chunk == null) {
            chunk = new ChunkModel(this.world, x, z);
            this.chunks.put(key, chunk);
        }

        return chunk;
    }

    @Override
    public Chunk provideChunk(final int x, final int z) {
        return getLoadedChunk(x, z);
    }

    @Override
    public String makeString() {
        return "SchematicChunkCache";
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return MoreObjects.firstNonNull(getLoadedChunk(x, z), this.emptyChunk);
    }

    @Override
    public void unloadChunk(int x, int z) {
        // NOOP: schematic chunks are part of the schematic world and are never unloaded separately

    }
}