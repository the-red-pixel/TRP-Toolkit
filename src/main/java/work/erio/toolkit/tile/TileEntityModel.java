package work.erio.toolkit.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.misc.BlockInfo;

import java.util.*;

public class TileEntityModel extends TileEntity {
//    private BlockInfo[] blockInfos;

    private Map<BlockPos, BlockInfo> blockInfos;

    public TileEntityModel() {
        this.blockInfos = new HashMap<>();
//        Arrays.fill(blockInfos, new BlockInfo(Blocks.AIR, 0));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();

        for (Map.Entry<BlockPos, BlockInfo> pair : this.blockInfos.entrySet()) {
            NBTTagCompound tag = new NBTTagCompound();
            BlockPos pos = pair.getKey();
            BlockInfo info = pair.getValue();
            tag.setInteger("x", pos.getX());
            tag.setInteger("y", pos.getY());
            tag.setInteger("z", pos.getZ());
            tag.setInteger("id", Block.getIdFromBlock(info.getBlock()));
            tag.setInteger("meta", info.getMeta());
            tagList.appendTag(tag);
        }

        compound.setTag("maps", tagList);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList("maps", Constants.NBT.TAG_COMPOUND);
        Map<BlockPos, BlockInfo> temp = new HashMap<>();
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            int x = tag.getInteger("x");
            int y = tag.getInteger("y");
            int z = tag.getInteger("z");
            int id = tag.getInteger("id");
            int meta = tag.getInteger("meta");
            BlockPos pos = new BlockPos(x, y, z);
            BlockInfo info = new BlockInfo(Block.getBlockById(id), meta);
            temp.put(pos, info);
        }
        this.blockInfos = temp;
    }

    public void setBlockInfos(Map<BlockPos, BlockInfo> blockInfos) {
        this.blockInfos = blockInfos;
        markDirty();
    }

    public Map<BlockPos, BlockInfo> getBlockInfos() {
        return blockInfos;
    }

    public void shuffle() {
        Map<BlockPos, BlockInfo> temp = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < 4096; i++) {
            if (rand.nextBoolean()) {
                int x = rand.nextInt(16);
                int y = rand.nextInt(16);
                int z = rand.nextInt(16);
                int meta = rand.nextInt(16);
                Block block = Block.getBlockById(rand.nextInt(100));
                temp.put(new BlockPos(x, y, z), new BlockInfo(block, 0));
            }
        }
        this.blockInfos = temp;
        markDirty();
    }

    private boolean notOpaque(BlockInfo info) {
        Block block = info.getBlock();
        IBlockState blockState = block.getStateFromMeta(info.getMeta());
        return !block.isOpaqueCube(blockState);
    }

    public void truncate() {
        List<BlockPos> delList = new LinkedList<>();
        for (Map.Entry<BlockPos, BlockInfo> pair : this.blockInfos.entrySet()) {
            BlockInfo up = blockInfos.get(pair.getKey().up());
            BlockInfo down = blockInfos.get(pair.getKey().down());
            BlockInfo south = blockInfos.get(pair.getKey().south());
            BlockInfo north = blockInfos.get(pair.getKey().north());
            BlockInfo east = blockInfos.get(pair.getKey().east());
            BlockInfo west = blockInfos.get(pair.getKey().west());
            if (up == null || down == null || south == null || north == null || east == null || west == null) {
                continue;
            } else {
                delList.add(pair.getKey());
            }
//            if (notOpaque(up) && notOpaque(down) && notOpaque(south) && notOpaque(north) && notOpaque(east) && notOpaque(west)) {
//                delList.add(pair.getKey());
//            }
        }

        for (BlockPos pos : delList) {
            blockInfos.remove(pos);
        }

        markDirty();
    }

    public void fill() {
        Map<BlockPos, BlockInfo> temp = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    Block block = Block.getBlockById(rand.nextInt(256));
                    temp.put(new BlockPos(i, j, k), new BlockInfo(block, 0));
                }
            }
        }
        blockInfos = temp;
        markDirty();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }
}
