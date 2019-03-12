package work.erio.toolkit.plugin;

import work.erio.toolkit.util.ClassUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class PluginManager {
    private static PluginManager instance;
    //    private List<AbstractPlugin> pluginList;
    private Map<String, Class<? extends AbstractPlugin>> pluginMap;
    public static final AbstractPlugin EMPTY = new PluginEmpty();

    private PluginManager() {
//        this.pluginList = new ArrayList<>();
        this.pluginMap = new HashMap<>();
        loadPlugins();
        System.out.println(pluginMap);
    }

    private void loadPlugins() {
        try {
            List<Class> classes = ClassUtils.findAllClasses("work.erio.toolkit.plugin");
            for (Class c : classes) {
                System.out.println(c.getClass().getSimpleName() + " " + (c.getSuperclass() == AbstractPlugin.class));
                if (c.getSuperclass() == AbstractPlugin.class) {
                    this.pluginMap.put(((AbstractPlugin) c.newInstance()).getName(), c);
                }
            }
        } catch (URISyntaxException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public List<AbstractPlugin> getPluginList() {
//        return pluginList;
//    }

    public String[] getPluginNameList() {
        return pluginMap.keySet().toArray(new String[0]);
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

//    public AbstractPlugin getPluginByName(String name) {
//        return pluginList.stream().filter(p -> p.getName().toUpperCase().equals(name.toUpperCase())).findFirst().orElse(getPluginByName("Empty"));
//    }

//    public Class getPluginClassByName(String name) {
//        return Optional.ofNullable(pluginMap.get(name)).orElse(pluginMap.get("Empty"));
//    }

//    public AbstractPlugin getPluginByName(String name) {
//        try {
//            return (AbstractPlugin) getPluginClassByName(name).newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        // 改回string和object对
//    }

    public AbstractPlugin createPlugin(String name) {
        Class<? extends AbstractPlugin> clazz = this.pluginMap.get(name);
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return EMPTY;
        }
    }
}
