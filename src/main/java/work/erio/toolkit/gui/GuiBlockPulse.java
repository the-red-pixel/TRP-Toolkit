package work.erio.toolkit.gui;

import com.rabbit.gui.RabbitGui;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.component.control.ToggleButton;
import com.rabbit.gui.show.Show;
import net.minecraft.client.resources.I18n;
import work.erio.toolkit.tile.TileEntityPulse;

public class GuiBlockPulse extends Show {

    //    protected GuiSwitch<Boolean> loopSwitch;
//    protected GuiSwitch<Boolean> modeSwitch;
    private TileEntityPulse te;

    public GuiBlockPulse(TileEntityPulse te) {
        this.te = te;
    }

    @Override
    public void setup() {
        super.setup();
        TextBox textBox = new TextBox(this.width / 2 - 150, 50, 300, 20, "");
        ToggleButton loopSwitch = new ToggleButton(width / 2 - 50, height / 2, 100, 20, "Loop", this.te.isLoop());
//        textBox.setText(this.te.getData());
//        textBox.setTextChangedListener((textbox, previousText) -> this.te.setData(textBox.getText()));
//        loopSwitch.setClickListener(button -> {
//            this.te.setLoop(!((ToggleButton) button).getToggleState());
//        });
        textBox.setText(this.te.getData());


        Button cancelBtn = new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"));
        Button confirmBtn = new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"));

        cancelBtn.setClickListener(button -> RabbitGui.proxy.getCurrentStage().close());
        confirmBtn.setClickListener(button -> {
            this.te.setData(textBox.getText());
            this.te.setLoop(loopSwitch.getToggleState());
            RabbitGui.proxy.getCurrentStage().close();
        });

        registerComponent(textBox);
        registerComponent(loopSwitch);
        registerComponent(cancelBtn);
        registerComponent(confirmBtn);
    }
}
