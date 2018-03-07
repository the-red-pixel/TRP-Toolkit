package work.erio.toolkit.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Erioifpud on 2018/3/5.
 */
public class TileEntityKeypad extends TileEntity {
    private int power = 0;

    public int getPower(){
        return power;
    }

    public void insert(int x) {
        int result = this.power * 10 + x;
        if (result > this.power) {
            this.power = result;
        }
    }

    public void clear() {
        this.power = 0;
    }

    public void undo() {
        this.power /= 10;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("power", this.power);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.power = compound.getInteger("power");
    }
}
