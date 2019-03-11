package work.erio.toolkit.plugin;

import net.minecraft.nbt.NBTTagCompound;

public interface ISerielizable {
    NBTTagCompound writeToNBT(NBTTagCompound compound);

    void readFromNBT(NBTTagCompound compound);
}
