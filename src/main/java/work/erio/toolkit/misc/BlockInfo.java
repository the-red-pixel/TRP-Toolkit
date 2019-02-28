package work.erio.toolkit.misc;

import net.minecraft.block.Block;

public class BlockInfo {
    private Block block;
    private int meta;

    public BlockInfo(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "BlockInfo{" +
                "block=" + block +
                ", meta=" + meta +
                '}';
    }
}
