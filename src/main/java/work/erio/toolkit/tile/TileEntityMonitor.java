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
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.common.ToolkitToast;
import work.erio.toolkit.gui.FrameChart;
import work.erio.toolkit.util.TextUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.DoubleStream;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class TileEntityMonitor extends TileEntity implements ITickable {
    //private ItemStack stack = ItemStack.EMPTY;
    private int themeIndex = -1;
    private Queue<Integer> queue = new LinkedList<>();
    private int currentValue = 0;
    private int delayCounter = 2;
    private int value;
    private int lastValue = Integer.MIN_VALUE;
    private ToolkitToast.Builder builder;

    public TileEntityMonitor() {
        builder = ToolkitToast.builder("").setStack(new ItemStack(ModBlocks.blockMonitor));
    }

    public void showChart() {
        new Thread(this::processData).start();
    }

    private void processData() {
        double[] yData = queue.stream().mapToDouble(Integer::doubleValue).toArray();
        double[] xData = DoubleStream.iterate(1, n -> n + 1).limit(yData.length).toArray();
        FrameChart frameChart = FrameChart.getInstance();
        frameChart.addChart(pos, xData, yData);
        frameChart.setVisible(true);
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

    public boolean hasTheme() {
        return themeIndex >= 0 && themeIndex < ToolkitToast.Theme.values().length;
    }

    public void nextTheme() {
        if (themeIndex >= ToolkitToast.Theme.values().length - 1) {
            themeIndex = -1;
        } else {
            themeIndex++;
        }
        markDirty();
    }

    public ToolkitToast.Theme getTheme(int themeIndex) {
        if (themeIndex >= 0 && themeIndex < ToolkitToast.Theme.values().length) {
            return ToolkitToast.Theme.values()[themeIndex];
        } else if (themeIndex == -1) {
            return null;
        } else {
            return ToolkitToast.Theme.PRIMARY;
        }
    }

    public ToolkitToast.Theme getTheme() {
        return getTheme(this.themeIndex);
    }

    public int getThemeIndex() {
        return themeIndex;
    }

    public void setThemeIndex(int index) {
        this.themeIndex = index;
        markDirty();
        IBlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
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
        compound.setInteger("theme", this.themeIndex);
        compound.setIntArray("data", queue.stream().mapToInt(i -> i).toArray());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.value = compound.getInteger("value");
        this.themeIndex = compound.getInteger("theme");
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
        if (hasTheme()) {
            builder.setTitle(String.valueOf(world.getWorldTime() % 48000L)).setSubtitle(String.valueOf(value)).setTheme(getTheme(themeIndex));
            ToolkitToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), builder);
        }
    }

    private void updateCounter(BlockPos pos) {
        delayCounter--;
        if (delayCounter <= 0) {
            lastValue = currentValue;
            currentValue = getData(pos);
            setThemeIndex(getThemeIndex());
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
