package work.erio.toolkit.module;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleManager {

    private int index;
    private static ModuleManager instance;
    private List<IModule> moduleList;

    public ModuleManager() {
        this.moduleList = Arrays.asList(
                new ModuleTest(),
                new ModuleComparator()
        );
        this.index = 0;
    }

    public void addModule(IModule module) {
        if (moduleList.contains(module)) {
            moduleList.add(module);
        }
    }

    public List<IModule> getFullModuleList() {
        return moduleList;
    }

    public List<IModule> getEnabledModuleList() {
        Stream<IModule> enabledModuleStream = moduleList.stream().filter(m -> ((AbstractModule) m).isEnabled());
        List<IModule> enabledModules = enabledModuleStream.collect(Collectors.toList());
        return enabledModules;
    }

    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
        }
        return instance;
    }

    public void nextModule() {
        Stream<IModule> enabledModuleStream = moduleList.stream().filter(m -> ((AbstractModule) m).isEnabled());
        List<IModule> enabledModules = enabledModuleStream.collect(Collectors.toList());
        int index = this.index >= enabledModules.size() - 1 ? 0 : this.index + 1;
        this.index = index;
    }

    public void prevModule() {
        Stream<IModule> enabledModuleStream = moduleList.stream().filter(m -> ((AbstractModule) m).isEnabled());
        List<IModule> enabledModules = enabledModuleStream.collect(Collectors.toList());
        int index = this.index <= 0 ? enabledModules.size() - 1 : this.index - 1;
        this.index = index;
    }

    public IModule getCurrentModule() {
        Stream<IModule> enabledModuleStream = moduleList.stream().filter(m -> ((AbstractModule) m).isEnabled());
        List<IModule> enabledModules = enabledModuleStream.collect(Collectors.toList());
        if (enabledModules.isEmpty()) {
            return null;
        }
        return enabledModules.get(this.index);
    }

    public IModule getModuleByClass() {
        return null;
    }
}
