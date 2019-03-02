package work.erio.toolkit.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import work.erio.toolkit.ModItems;
import work.erio.toolkit.module.ModuleManager;

@Mod.EventBusSubscriber
public class EventManager {
//    private static EventManager instance;
    private static ModuleManager moduleManager = ModuleManager.getInstance();

//    public static EventManager getInstance() {
//        if (instance == null) {
//            instance = new EventManager();
//        }
//        return instance;
//    }

    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() == ModItems.itemUniversalWrench) {
            moduleManager.getCurrentModule().onRender(evt);
        }
    }
}
