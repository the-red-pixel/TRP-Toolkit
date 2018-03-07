package work.erio.toolkit;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import work.erio.toolkit.proxy.CommonProxy;

@Mod(modid = Toolkit.MODID, name = Toolkit.NAME, version = Toolkit.VERSION)
public class Toolkit {
    public static final String MODID = "toolkit";
    public static final String NAME = "TRP Toolkit";
    public static final String VERSION = "1.0.0";
    @Mod.Instance
    public static Toolkit instance;
    @SidedProxy(clientSide = "work.erio.toolkit.proxy.ClientProxy", serverSide = "work.erio.toolkit.proxy.ServerProxy")
    public static CommonProxy proxy;
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
