package work.erio.toolkit.tile;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.misc.BlockInfo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TileEntityModel extends TileEntity {
    private BlockInfo[] blockInfos;

    public TileEntityModel() {
        this.blockInfos = new BlockInfo[4096];
        Arrays.fill(blockInfos, new BlockInfo(Blocks.AIR, 0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();

        for (BlockInfo blockInfo : blockInfos) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("id", Block.getIdFromBlock(blockInfo.getBlock()));
            tag.setInteger("meta", blockInfo.getMeta());
            tagList.appendTag(tag);
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
        List<BlockInfo> list = new LinkedList<>();
        for (int i = 0; i < 4096; i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            int id = tag.getInteger("id");
            int meta = tag.getInteger("meta");
            list.add(new BlockInfo(Block.getBlockById(id), meta));
        }
        this.blockInfos = list.stream().toArray(BlockInfo[]::new);
    }

//    public void setBlockInfo(int x, int y, int z, BlockInfo blockInfo) {
//        this.blockInfos[x][y][z] = blockInfo;
//        markDirty();
//    }
//
//    public void setBlock(int x, int y, int z, Block block) {
//        this.blockInfos[x][y][z].setBlock(block);
//        markDirty();
//    }
//
//    public void setMeta(int x, int y, int z, int meta) {
//        this.blockInfos[x][y][z].setMeta(meta);
//        markDirty();
//    }
//
//    public BlockInfo getBlockInfo(int x, int y, int z) {
//        markDirty();
//        return blockInfos[x][y][z];
//    }

    public BlockInfo[] getBlockInfos() {
        markDirty();
        return blockInfos;
    }

    public void shuffle() {
        for (BlockInfo blockInfo : blockInfos) {
            if (new Random().nextBoolean()) {
                Block block = Block.getBlockById(new Random().nextInt(100));
                blockInfo.setBlock(block);
            }
        }
        markDirty();
    }
}
