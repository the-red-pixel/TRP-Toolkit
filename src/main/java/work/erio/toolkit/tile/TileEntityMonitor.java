package work.erio.toolkit.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class TileEntityMonitor extends TileEntity implements ITickable {
    private ItemStack stack = ItemStack.EMPTY;
    private int currentValue = 0;
    private int delayCounter = 2;
    private int value;
    private int lastValue = Integer.MIN_VALUE;

    public void setStack(ItemStack stack) {
        this.stack = stack;
        markDirty();
        if (world != null) {
            IBlockState state = world.getBlockState(getPos());
            world.notifyBlockUpdate(getPos(), state, state, 3);
        }
    }

    public ItemStack getStack() {
        return stack;
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
    }

    @Override
    public void update() {
        IBlockState down = world.getBlockState(pos.down());
        if (!world.isRemote && world.getTileEntity(pos.down()) instanceof TileEntityComparator) {
            updateCounter(pos.down());
        }
    }

    private void printCurrentState(int value) {
        if (hasItem()) {
            String s = String.format("[%s] - %d", getStack().getDisplayName(), value);
            TextComponentString message = new TextComponentString(s);
            message.getStyle().setColor(TextFormatting.YELLOW);
            Minecraft.getMinecraft().player.sendStatusMessage(message, false);

        }
    }

    private void updateCounter(BlockPos pos) {
        delayCounter--;
        if (delayCounter <= 0) {
            TileEntity tileEntity = world.getTileEntity(pos);
            tileEntity.markDirty();
            lastValue = currentValue;
            currentValue = ((TileEntityComparator) tileEntity).getOutputSignal();
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
}
