package work.erio.toolkit.gui;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.ToggleButton;
import com.rabbit.gui.component.display.Panel;
import com.rabbit.gui.component.list.ScrollableDisplayList;
import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.component.list.entries.MultiComponentListEntry;
import com.rabbit.gui.show.Show;
import work.erio.toolkit.module.AbstractModule;
import work.erio.toolkit.module.ModuleManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuiSettings extends Show {
    private List<ListEntry> moduleList;
    private Panel panel;
    private Panel contentPanel;
//    private ScrollableDisplayList scrollableDisplayList;

    public GuiSettings() {
//        moduleList = loadModules();
    }

    @Override
    public void setup() {
        super.setup();
        panel = new Panel((int) (width * 0.2), (int) (height * 0.2), (int) (width * 0.6), (int) (height * 0.6))
                .setCanDrag(true)
                .setFocused(true);
//        Tab tab = new ItemTab(0, 0, 25, 25, 90, Blocks.REDSTONE_BLOCK);
//
//        panel.registerComponent(tab);


//        contentPanel = new Panel(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
//
//
//        panel.registerComponent(contentPanel);
        showModuleSettingPanel();


        registerComponent(panel);

//        Panel test = new Panel(10, 10, 200, 200).setCanDrag(true).setVisible(true);
//        test.registerComponent(new TextTab(0, 0, test.getWidth(), test.getHeight(), "123"));
//        registerComponent(test);

    }

    private void showModuleSettingPanel() {
//        contentPanel = new Panel(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
        this.moduleList = loadModules(panel);
        ScrollableDisplayList scrollableDisplayList =  new ScrollableDisplayList(0, 0, panel.getWidth(), panel.getHeight(),
                30, moduleList);
        panel.registerComponent(scrollableDisplayList);
    }

    private List<ListEntry> loadModules(GuiWidget parent) {
        ModuleManager moduleManager = ModuleManager.getInstance();
        Stream<ListEntry> stream =  moduleManager.getFullModuleList().stream().map(m -> {
            MultiComponentListEntry listEntry = new MultiComponentListEntry();
            if (m instanceof AbstractModule) {
                ToggleButton toggleButton = new ToggleButton(0, 0, parent.getWidth() - 10, 20, m.getTitle(), ((AbstractModule) m).isEnabled());
                toggleButton.setClickListener(button -> {
                    System.out.println(toggleButton.getToggleState());
                    ((AbstractModule) m).setEnabled(!toggleButton.getToggleState());
                });
                listEntry.registerComponent(toggleButton, 5, 10);
            } else {
                listEntry.registerComponent(new Button(0, 0, parent.getWidth() - 10, 20, "#invalid module#").setIsEnabled(false), 5, 10);
            }
            return listEntry;
        });
        return stream.collect(Collectors.toList());
    }
}
