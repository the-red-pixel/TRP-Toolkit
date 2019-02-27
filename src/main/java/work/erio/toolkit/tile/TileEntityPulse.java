package work.erio.toolkit.tile;

import com.rabbit.gui.RabbitGui;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import work.erio.toolkit.gui.GuiBlockPulse;

public class TileEntityPulse extends TileEntity implements ITickable {

    private String data;
    private boolean isLoop;
    private int power;
    private int index;
    private boolean running;

    public TileEntityPulse() {
        this.data = "";
        this.isLoop = false;
        this.power = 0;
        this.index = 0;
        this.running = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.data = compound.getString("data");
        this.isLoop = compound.getBoolean("loop");
        this.index = compound.getInteger("index");
        this.power = compound.getInteger("power");
        this.running = compound.getBoolean("running");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("data", this.data);
        compound.setBoolean("loop", this.isLoop);
        compound.setInteger("index", this.index);
        compound.setInteger("power", this.power);
        compound.setBoolean("running", this.running);
        return compound;
    }

//    public void showGui() {
//        Minecraft.getMinecraft().displayGuiScreen(new GuiBlockPulse(this));
//    }

    public void showGui() {
        RabbitGui.proxy.display(new GuiBlockPulse(this));
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

    public void emit() {
        if (!this.isLoop) {

        }
    }

    private void reset() {
        this.index = 0;
        this.setRunning(false);
        this.setPower(0);
    }

    public void toggleRunning() {
        this.running = !this.running;
        markDirty();
    }

    @Override
    public void update() {
        if (this.data.isEmpty()) {
            reset();
            return;
        }
        if (!this.running) {
            reset();
            return;
        }
        if (this.isLoop) {
            char c = this.data.charAt(this.index);
            this.power = c == '0' ? 0 : 15;
            markDirty();
            System.out.println(this.power);
            this.index++;
            if (this.index == this.data.length()) {
                this.index = 0;
            }
        } else {
//            if (this.index % 1 != 0) {
//                this.index += 0.5d;
//                return;
//            }
//
//            char c = this.data.charAt((int) this.index);
//            this.power = c == '0' ? 0 : 15;
//            markDirty();
//            this.index += 0.5d;
//            if (this.index >= this.data.length() - 1) {
//                this.setRunning(false);
//                this.index = 0;
//            }


            // -----------------------------

            char c = this.data.charAt(this.index);
            this.power = c == '0' ? 0 : 15;
            markDirty();
            this.index++;
            if (this.index == this.data.length()) {
                this.setRunning(false);
                this.index = 0;
            }
        }
    }

    public String getData() {
        markDirty();
        return data;
    }

    public void setPower(int power) {
        this.power = power;
        markDirty();
    }

    public boolean isLoop() {
        markDirty();
        return isLoop;
    }

    public void setRunning(boolean running) {
        this.running = running;
        markDirty();
    }

    public int getPower() {
        return power;
    }
}
