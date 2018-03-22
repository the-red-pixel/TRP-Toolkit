package work.erio.toolkit.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Created by Erioifpud on 2018/3/20.
 */
@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void test(TickEvent.PlayerTickEvent event) {
        /*
        if (event.phase == TickEvent.Phase.END) {
            if (FreecamHandler.getInstance().isState()) {
                event.player.noClip = true;
                event.player.onGround = false;
            }
        }
        */
    }
}
