package work.erio.toolkit.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.misc.BlockInfo;
import work.erio.toolkit.world.IModel;
import work.erio.toolkit.world.Model;
import work.erio.toolkit.world.NBTHelper;

import java.util.*;

public class TileEntityModel extends TileEntity {
//    private BlockInfo[] blockInfos;

//    private Map<BlockPos, BlockInfo> blockInfos;

    private IModel model;

    public TileEntityModel() {
//        this.blockInfos = new HashMap<>();
        this.model = new Model();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
//        NBTTagList tagList = new NBTTagList();
//
//        for (Map.Entry<BlockPos, BlockInfo> pair : this.blockInfos.entrySet()) {
//            NBTTagCompound tag = new NBTTagCompound();
//            BlockPos pos = pair.getKey();
//            BlockInfo info = pair.getValue();
//            tag.setInteger("x", pos.getX());
//            tag.setInteger("y", pos.getY());
//            tag.setInteger("z", pos.getZ());
//            tag.setInteger("id", Block.getIdFromBlock(info.getBlock()));
//            tag.setInteger("meta", info.getMeta());
//            tagList.appendTag(tag);
//        }
//
//        compound.setTag("maps", tagList);
//        System.out.println("writeToNBT " + compound);



//        ------------------------------
        NBTTagCompound modelCompound = new NBTTagCompound();

        int[] ids = new int[4096];
        byte[] metas = new byte[4096];
        for (byte i = 0; i < 16; i++) {
            for (byte j = 0; j < 16; j++) {
                for (byte k = 0; k < 16; k++) {
                    int index = i * 256 + j * 16 + k;
                    IBlockState blockState = this.model.getBlockState(i, j, k);
                    Block block = blockState.getBlock();
                    ids[index] = Block.REGISTRY.getIDForObject(block);
                    metas[index] = (byte) block.getMetaFromState(blockState);
                }
            }
        }
        modelCompound.setIntArray("id", ids);
        modelCompound.setByteArray("meta", metas);

        NBTTagList tiles = new NBTTagList();
        for (TileEntity tileEntity : this.model.getTileEntities()) {
            NBTTagCompound tileCompound = new NBTTagCompound();
            BlockPos pos = tileEntity.getPos();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            tileCompound.setInteger("x", x);
            tileCompound.setInteger("y", y);
            tileCompound.setInteger("z", z);

            NBTTagCompound tileDataCompound = NBTHelper.writeTileEntityToCompound(tileEntity);
            tileDataCompound.removeTag("x");
            tileDataCompound.removeTag("y");
            tileDataCompound.removeTag("z");
            tileCompound.setTag("data", tileDataCompound);

            tiles.appendTag(tileCompound);
        }
        modelCompound.setTag("tiles", tiles);

        compound.setTag("model", modelCompound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
//        System.out.println("readFromNBT " + compound);
//        NBTTagList tagList = compound.getTagList("maps", Constants.NBT.TAG_COMPOUND);
//        Map<BlockPos, BlockInfo> temp = new HashMap<>();
//        for (int i = 0; i < tagList.tagCount(); i++) {
//            NBTTagCompound tag = tagList.getCompoundTagAt(i);
//            int x = tag.getInteger("x");
//            int y = tag.getInteger("y");
//            int z = tag.getInteger("z");
//            int id = tag.getInteger("id");
//            int meta = tag.getInteger("meta");
//            BlockPos pos = new BlockPos(x, y, z);
//            BlockInfo info = new BlockInfo(Block.getBlockById(id), meta);
//            temp.put(pos, info);
//        }
//        this.blockInfos = temp;



//        ----------------------------------------
        NBTTagCompound modelCompound = compound.getCompoundTag("model");
        int[] ids = modelCompound.getIntArray("id");
        byte[] metas = modelCompound.getByteArray("meta");
        for (byte i = 0; i < 16; i++) {
            for (byte j = 0; j < 16; j++) {
                for (byte k = 0; k < 16; k++) {
                    int index = i * 256 + j * 16 + k;
                    Block block = Block.REGISTRY.getObjectById(ids[index]);
                    IBlockState blockState = block.getStateFromMeta(metas[index]);
                    this.model.setBlockState(i, j, k, blockState);
                }
            }
        }

        NBTTagList tileList = modelCompound.getTagList("tiles", Constants.NBT.TAG_COMPOUND);
        for (NBTBase nbtBase : tileList) {
            NBTTagCompound tile = (NBTTagCompound) nbtBase;
            int x = tile.getInteger("x");
            int y = tile.getInteger("y");
            int z = tile.getInteger("z");
            TileEntity tileEntity = NBTHelper.readTileEntityFromCompound(tile.getCompoundTag("data"));
            this.model.setTileEntity(new BlockPos(x, y, z), tileEntity);
        }
    }

    public IModel getModel() {
        return model;
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    //    public void setBlockInfos(Map<BlockPos, BlockInfo> blockInfos) {
//        this.blockInfos = blockInfos;
//        markDirty();
//    }
//
//    public Map<BlockPos, BlockInfo> getBlockInfos() {
//        return blockInfos;
//    }

//    public void shuffle() {
//        Map<BlockPos, BlockInfo> temp = new HashMap<>();
//        Random rand = new Random();
//        for (int i = 0; i < 4096; i++) {
//            if (rand.nextBoolean()) {
//                int x = rand.nextInt(16);
//                int y = rand.nextInt(16);
//                int z = rand.nextInt(16);
//                int meta = rand.nextInt(16);
//                Block block = Block.getBlockById(rand.nextInt(100));
//                temp.put(new BlockPos(x, y, z), new BlockInfo(block, 0));
//            }
//        }
//        this.blockInfos = temp;
//        markDirty();
//    }
//
//    private boolean notOpaque(BlockInfo info) {
//        Block block = info.getBlock();
//        IBlockState blockState = block.getStateFromMeta(info.getMeta());
//        return !block.isOpaqueCube(blockState);
//    }
//
//    public void truncate() {
//        List<BlockPos> delList = new LinkedList<>();
//        for (Map.Entry<BlockPos, BlockInfo> pair : this.blockInfos.entrySet()) {
//            BlockInfo up = blockInfos.get(pair.getKey().up());
//            BlockInfo down = blockInfos.get(pair.getKey().down());
//            BlockInfo south = blockInfos.get(pair.getKey().south());
//            BlockInfo north = blockInfos.get(pair.getKey().north());
//            BlockInfo east = blockInfos.get(pair.getKey().east());
//            BlockInfo west = blockInfos.get(pair.getKey().west());
//            if (up == null || down == null || south == null || north == null || east == null || west == null) {
//                continue;
//            } else {
//                delList.add(pair.getKey());
//            }
////            if (notOpaque(up) && notOpaque(down) && notOpaque(south) && notOpaque(north) && notOpaque(east) && notOpaque(west)) {
////                delList.add(pair.getKey());
////            }
//        }
//
//        for (BlockPos pos : delList) {
//            blockInfos.remove(pos);
//        }
//
//        markDirty();
//    }
//
//    public void fill() {
//        Map<BlockPos, BlockInfo> temp = new HashMap<>();
//        Random rand = new Random();
//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                for (int k = 0; k < 16; k++) {
//                    Block block = Block.getBlockById(rand.nextInt(256));
//                    temp.put(new BlockPos(i, j, k), new BlockInfo(block, 0));
//                }
//            }
//        }
//        blockInfos = temp;
//        markDirty();
//    }

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
        // Here we get the packet from the server and read it into our client side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }
}
