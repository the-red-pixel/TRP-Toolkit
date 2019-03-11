package work.erio.toolkit.gui;

import com.rabbit.gui.RabbitGui;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.DropDown;
import com.rabbit.gui.show.Show;
import net.minecraft.client.resources.I18n;
import work.erio.toolkit.plugin.AbstractPlugin;
import work.erio.toolkit.plugin.PluginManager;
import work.erio.toolkit.tile.TileEntityUniversalSign;

import java.util.List;

public class GuiBlockSign extends Show {
    private PluginManager manager = PluginManager.getInstance();
    private TileEntityUniversalSign te;
    private DropDown<AbstractPlugin> dropDown;

    public GuiBlockSign(TileEntityUniversalSign tileEntityUniversalSign) {
        this.te = tileEntityUniversalSign;
    }

    @Override
    public void setup() {
        super.setup();
        List<AbstractPlugin> list = manager.getPluginList();
        this.dropDown = new DropDown<>(this.width / 2 - 150, 50, 300, 20, list.toArray(new AbstractPlugin[0]));
        Button cancelBtn = new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"));
        Button confirmBtn = new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"));

        cancelBtn.setClickListener(button -> RabbitGui.proxy.getCurrentStage().close());
        confirmBtn.setClickListener(button -> {
//            this.te.setData(textBox.getText());
//            this.te.setLoop(loopSwitch.getToggleState());
            RabbitGui.proxy.getCurrentStage().close();
        });

        registerComponent(dropDown);
        registerComponent(confirmBtn);
        registerComponent(cancelBtn);
    }
}
