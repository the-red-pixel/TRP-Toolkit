package work.erio.toolkit.handler;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import work.erio.toolkit.Toolkit;

/**
 * Created by Erioifpud on 2018/3/20.
 */
@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        Toolkit.config.save();
    }
}
