package work.erio.toolkit.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import work.erio.toolkit.Toolkit;

/**
 * Created by Erioifpud on 2018/3/24.
 */
public class Configs {
    public static BlockCategory blockCategory = new BlockCategory();

    public void save() {
        ConfigManager.sync(Toolkit.MODID, Config.Type.INSTANCE);
    }

    @Config.LangKey("config.toolkit.block_configuration")
    @Config(modid = Toolkit.MODID, category = "block")
    public static class BlockCategory {
        @Config.LangKey("config.toolkit.monitor_record_limit")
        @Config.RangeInt(min = 1, max = 100)
        public static int monitorRecordLimit = 20;
    }
}
