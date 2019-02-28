package work.erio.toolkit.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.misc.BlockInfo;

import java.util.Arrays;
import java.util.Random;

public class TileEntityModel extends TileEntity {
    private BlockInfo[][][] blockInfos;

    public TileEntityModel() {
        this.blockInfos = new BlockInfo[16][16][16];
        for (BlockInfo[][] b1 : blockInfos) {
            for (BlockInfo[] b2 : b1) {
                Arrays.fill(b2, new BlockInfo(Blocks.AIR, 0));
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();

        for (BlockInfo[][] b1 : blockInfos) {
            for (BlockInfo[] b2 : b1) {
                for (BlockInfo b3 : b2) {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setInteger("id", Block.getIdFromBlock(b3.getBlock()));
                    tag.setInteger("meta", b3.getMeta());
                    tagList.appendTag(tag);
                }
            }
        }
        compound.setTag("blockInfos", tagList);
//        Minecraft.getMinecraft().world.setBlockState()
//        Block.getStateById()
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList("blockInfos", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    int index = i * 256 + j * 16 + k;
                    NBTTagCompound tag = tagList.getCompoundTagAt(index);
                    int id = tag.getInteger("id");
                    int meta = tag.getInteger("meta");
                    blockInfos[i][j][k] = new BlockInfo(Block.getBlockById(id), meta);
                }
            }
        }
    }

    public void setBlockInfo(int x, int y, int z, BlockInfo blockInfo) {
        this.blockInfos[x][y][z] = blockInfo;
        markDirty();
    }

    public void setBlock(int x, int y, int z, Block block) {
        this.blockInfos[x][y][z].setBlock(block);
        markDirty();
    }

    public void setMeta(int x, int y, int z, int meta) {
        this.blockInfos[x][y][z].setMeta(meta);
        markDirty();
    }

    public BlockInfo getBlockInfo(int x, int y, int z) {
        markDirty();
        return blockInfos[x][y][z];
    }

    public BlockInfo[][][] getBlockInfos() {
        markDirty();
        return blockInfos;
    }

    public void shuffle() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    if (new Random().nextBoolean()) {
                        Block block = Block.getBlockById(new Random().nextInt(100));
                        setBlockInfo(i, j, k, new BlockInfo(block, 1));
                    }
                }
            }
        }
        markDirty();
    }
}
