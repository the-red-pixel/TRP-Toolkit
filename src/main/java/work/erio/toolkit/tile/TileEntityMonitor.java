package work.erio.toolkit.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class TileEntityMonitor extends TileEntity implements ITickable {
    private int currentValue = 0;
    private int delayCounter = 2;
    private int value;
    private int lastValue = Integer.MIN_VALUE;

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
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.value = compound.getInteger("value");
    }

    @Override
    public void update() {
        IBlockState down = world.getBlockState(pos.down());
        if (!world.isRemote && world.getTileEntity(pos.down()) instanceof TileEntityComparator) {
            updateCounter(pos.down());
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
                System.out.println(currentValue);
                value = currentValue;
                markDirty();
                IBlockState state = world.getBlockState(getPos());
                world.notifyBlockUpdate(getPos(), state, state, 3);
            }
            delayCounter = 2;
        }
    }
}
