package work.erio.toolkit.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import work.erio.toolkit.gui.GuiBlockPulse;

public class TileEntityPulse extends TileEntity implements ITickable {

    private String data;
    private boolean isLoop;
    private boolean isRedstoneTick;

    public TileEntityPulse() {
        this.data = "";
        this.isLoop = false;
        this.isRedstoneTick = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.data = compound.getString("data");
        this.isRedstoneTick = compound.getBoolean("redstoneTick");
        this.isLoop = compound.getBoolean("loop");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("data", this.data);
        compound.setBoolean("loop", this.isLoop);
        compound.setBoolean("redstoneTick", this.isRedstoneTick);
        return compound;
    }

    public void showGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBlockPulse(this));
    }

//    public void updateState(String data, boolean loop, boolean tick) {
//        this.data = data;
//        this.isLoop = loop;
//        this.isRedstoneTick = tick;
//        markDirty();
//    }


    public void setData(String data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            sb.append(c == '0' ? '0' : '1');
        }
        this.data = sb.toString();
        markDirty();
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
        markDirty();
    }

    public void setRedstoneTick(boolean redstoneTick) {
        isRedstoneTick = redstoneTick;
        markDirty();
    }

    public void emit() {
        if (!this.isLoop) {

        }
    }

    @Override
    public void update() {
        if (this.isLoop) {
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);

            }
        }
    }

    public String getData() {
        return data;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public boolean isRedstoneTick() {
        return isRedstoneTick;
    }
}
