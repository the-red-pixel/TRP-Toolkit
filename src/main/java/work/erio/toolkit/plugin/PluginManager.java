package work.erio.toolkit.plugin;

import work.erio.toolkit.util.ClassUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private static PluginManager instance;
    private List<AbstractPlugin> pluginList;

    private PluginManager() {
        this.pluginList = new ArrayList<>();
        loadPlugins();
        System.out.println(pluginList);
    }

    private void loadPlugins() {
        try {
            List<Class> classes = ClassUtils.findAllClasses("work.erio.toolkit.plugin");
            for (Class c : classes) {
                System.out.println(c.getClass().getSimpleName() + " " + (c.getSuperclass() == AbstractPlugin.class));
                if (c != AbstractPlugin.class && c.getSuperclass() == AbstractPlugin.class) {
                    this.pluginList.add((AbstractPlugin) c.newInstance());
                }
            }
        } catch (URISyntaxException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public List<AbstractPlugin> getPluginList() {
        return pluginList;
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public AbstractPlugin getPluginByName(String name) {
        return pluginList.stream().filter(p -> p.getName().toUpperCase().equals(name.toUpperCase())).findFirst().orElse(getPluginByName("Empty"));
    }
}
