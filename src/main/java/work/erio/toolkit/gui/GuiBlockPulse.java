package work.erio.toolkit.gui;

import com.rabbit.gui.show.Show;
import work.erio.toolkit.gui.component.GuiSwitch;
import work.erio.toolkit.tile.TileEntityPulse;

import java.io.IOException;

public class GuiBlockPulse extends Show {

    protected GuiSwitch<Boolean> loopSwitch;
    protected GuiSwitch<Boolean> modeSwitch;

    public GuiBlockPulse(TileEntityPulse te) {
        super(te);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.textField.setText(this.te.getData());
        this.loopSwitch = new GuiSwitch(3, width / 2 - 50, height / 2, 100, 20,
                new Boolean[]{true, false}, "Loop");
        this.modeSwitch = new GuiSwitch(4, width / 2 - 50, height / 2 + 20, 100, 20,
                new Boolean[]{true, false}, "RedstoneTick");
        this.loopSwitch.setValue(this.te.isLoop());
        this.modeSwitch.setValue(this.te.isRedstoneTick());
    }

    @Override
    public void setup() {
        super.setup();
        
    }
}
