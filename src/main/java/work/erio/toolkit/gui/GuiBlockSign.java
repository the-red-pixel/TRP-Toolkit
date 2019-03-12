package work.erio.toolkit.gui;

import com.rabbit.gui.RabbitGui;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.DropDown;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.component.display.TextLabel;
import com.rabbit.gui.show.Show;
import net.minecraft.client.resources.I18n;
import work.erio.toolkit.plugin.AbstractPlugin;
import work.erio.toolkit.plugin.IInputable;
import work.erio.toolkit.plugin.PluginManager;
import work.erio.toolkit.tile.TileEntityUniversalSign;

import java.awt.*;
import java.util.List;

public class GuiBlockSign extends Show {
    private PluginManager manager = PluginManager.getInstance();
    private TileEntityUniversalSign te;
    private DropDown<String> dropDown;

    public GuiBlockSign(TileEntityUniversalSign tileEntityUniversalSign) {
        this.te = tileEntityUniversalSign;
    }

    @Override
    public void setup() {
        super.setup();
        AbstractPlugin plugin = te.getPlugin();
//        List<AbstractPlugin> list = manager.getPluginList();
        String[] list = manager.getPluginNameList();
        TextLabel label = new TextLabel(this.width / 2 - 150, 20, 300, 20, Color.ORANGE, plugin.getDescription());
        this.dropDown = new DropDown<>(this.width / 2 - 150, 50, 300, 20, list);
        Button cancelBtn = new Button(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel"));
        Button confirmBtn = new Button(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done"));

        cancelBtn.setClickListener(button -> RabbitGui.proxy.getCurrentStage().close());
        confirmBtn.setClickListener(button -> {
            System.out.println(this.dropDown.getSelectedIdentifier());
            this.te.setPluginByName(this.dropDown.getSelectedIdentifier());
            RabbitGui.proxy.getCurrentStage().close();
        });

        initDropdownValue();

        if (plugin instanceof IInputable) {
            IInputable inputable = ((IInputable) plugin);
            int count = inputable.getInputCount();
            for (int i = 1; i <= count; i++) {
                final int index = i - 1;
                TextBox textBox = new TextBox(this.width / 2 - 150, 50 + 25 * i, 300, 20);
                textBox.setTextChangedListener((textbox, previousText) -> {
                    inputable.onInputChange(index, textbox.getText(), previousText);
                });
                registerComponent(textBox);
            }
        }

        registerComponent(dropDown);
        registerComponent(confirmBtn);
        registerComponent(cancelBtn);
    }

    private void initDropdownValue() {
        dropDown.setDefaultItem(this.te.getPlugin().getName());
    }
}
