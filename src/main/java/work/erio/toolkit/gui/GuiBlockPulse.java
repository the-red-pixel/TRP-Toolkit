package work.erio.toolkit.gui;

import net.minecraft.client.gui.GuiButton;
import work.erio.toolkit.gui.component.GuiSwitch;
import work.erio.toolkit.tile.TileEntityPulse;

import java.io.IOException;

public class GuiBlockPulse extends AbstractGuiTextScreen<TileEntityPulse> {

    protected GuiSwitch<Boolean> loopSwitch;
    protected GuiSwitch<Boolean> modeSwitch;

    public GuiBlockPulse(TileEntityPulse te) {
        super(te);
        loopSwitch = new GuiSwitch(3, width / 2, height / 2, 100, 20,
                new Object[] {true, false}, "Loop");
        modeSwitch = new GuiSwitch(4, width / 2, height / 2 + 20, 100, 20,
                new Object[] {true, false}, "RedstoneTick");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.textField.setText(this.te.getData());
        this.loopSwitch.setValue(this.te.isLoop());
        this.modeSwitch.setValue(this.te.isRedstoneTick());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        loopSwitch.mouseClicked(mouseButton);
        modeSwitch.mouseClicked(mouseButton);
    }

    @Override
    protected void onConfirm() {
        boolean loop = (boolean) this.loopSwitch.getSelectedItem();
        boolean tick = (boolean) this.modeSwitch.getSelectedItem();
        this.te.setData(this.textField.getText());
        this.te.setLoop(loop);
        this.te.setRedstoneTick(tick);
        this.te.emit();
    }

    @Override
    protected String getTitle() {
        return "Pulse Setting";
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.loopSwitch.drawButton(this.mc, mouseX, mouseY, partialTicks);
        this.modeSwitch.drawButton(this.mc, mouseX, mouseY, partialTicks);
    }
}
