package work.erio.toolkit.tile;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import work.erio.toolkit.common.ToolkitToast;
import work.erio.toolkit.util.TextUtils;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class TileEntityMonitor extends TileEntity implements ITickable {
    private ItemStack stack = ItemStack.EMPTY;
    private Queue<Integer> queue = new LinkedList<>();
    private int currentValue = 0;
    private int delayCounter = 2;
    private int value;
    private int lastValue = Integer.MIN_VALUE;
    private ToolkitToast.Builder builder;

    public TileEntityMonitor() {
        builder = ToolkitToast.builder("").setStack(stack);
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    public void getDataSet() {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(world.getTotalWorldTime()).append("] ");
        for (Integer i : queue) {
            sb.append(i).append("  ");
        }
        TextUtils.printMessage(Minecraft.getMinecraft().player, sb.substring(0, sb.length() - 2), TextFormatting.WHITE);
    }

    public void addDataPoint(int n) {
        while (queue.size() >= 20) {
            queue.poll();
        }
        queue.add(n);
        markDirty();
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    public boolean hasItem() {
        return !stack.isEmpty();
    }

    public int getValue() {
        return value;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("value", this.value);
        if (!stack.isEmpty()) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
        compound.setIntArray("data", queue.stream().mapToInt(i -> i).toArray());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.value = compound.getInteger("value");
        if (compound.hasKey("item")) {
            stack = new ItemStack(compound.getCompoundTag("item"));
        } else {
            stack = ItemStack.EMPTY;
        }
        int[] data = compound.getIntArray("data");
        Queue<Integer> tmpQueue = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            tmpQueue.add(data[i]);
        }
        queue = tmpQueue;
    }

    @Override
    public void update() {
        IBlockState down = world.getBlockState(pos.down());
        if (!world.isRemote) {
            updateCounter(pos.down());
        }
    }

    private void printCurrentState(int value) {
        if (hasItem()) {
            //String s = String.format("[%s] - %d", getStack().getDisplayName(), value);
            //TextUtils.printMessage(Minecraft.getMinecraft().player, s, TextFormatting.YELLOW);
            builder.setTitle(stack.getDisplayName()).setSubtitle(String.valueOf(value));
            ToolkitToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), builder);
        }
    }

    private void updateCounter(BlockPos pos) {
        delayCounter--;
        if (delayCounter <= 0) {
            lastValue = currentValue;
            currentValue = getData(pos);
            addDataPoint(currentValue);
            if (currentValue != lastValue) {
                value = currentValue;
                markDirty();
                IBlockState state = world.getBlockState(getPos());
                world.notifyBlockUpdate(getPos(), state, state, 3);
                printCurrentState(value);
            }
            delayCounter = 2;
        }
    }

    private int getData(BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null) {
            tileEntity.markDirty();
        }
        IBlockState state = world.getBlockState(pos);
        if (tileEntity instanceof TileEntityComparator) {
            return ((TileEntityComparator) tileEntity).getOutputSignal();
        } else if (tileEntity instanceof TileEntityKeypad) {
            return ((TileEntityKeypad) tileEntity).getPower();
        } else if (state.getBlock() == Blocks.REDSTONE_WIRE) {
            return Blocks.REDSTONE_WIRE.getMetaFromState(state);
        } else if (tileEntity instanceof IInventory) {
            return Container.calcRedstone(tileEntity);
        } else if (tileEntity instanceof BlockJukebox.TileEntityJukebox) {
            ItemStack stack = ((BlockJukebox.TileEntityJukebox) tileEntity).getRecord();
            return Item.getIdFromItem(stack.getItem()) + 1 - Item.getIdFromItem(Items.RECORD_13);
        } else if (tileEntity instanceof TileEntityDaylightDetector) {
            return state.getValue(BlockDaylightDetector.POWER);
        } else if (state.getBlock() instanceof BlockLiquid) {
            return state.getValue(BlockLiquid.LEVEL);
        } else if (state.getBlock() instanceof BlockPressurePlateWeighted) {
            return state.getValue(BlockPressurePlateWeighted.POWER);
        }
        return 0;
    }
}
