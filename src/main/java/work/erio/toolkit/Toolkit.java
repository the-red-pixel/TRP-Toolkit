package work.erio.toolkit;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import work.erio.toolkit.config.Configs;
import work.erio.toolkit.proxy.CommonProxy;

@Mod(modid = Toolkit.MODID, name = Toolkit.NAME, version = Toolkit.VERSION)
public class Toolkit {
    public static final String MODID = "toolkit";
    public static final String NAME = "TRP Toolkit";
    public static final String VERSION = "1.4.0";
    @Mod.Instance(MODID)
    public static Toolkit INSTANCE;
    @SidedProxy(clientSide = "work.erio.toolkit.proxy.ClientProxy", serverSide = "work.erio.toolkit.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static Configs config;
    private boolean obfuscated;

    public static Logger logger = LogManager.getLogger(Toolkit.NAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
        config = new Configs();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    public static final CreativeTabs TRP_TOOLKIT = new CreativeTabs("toolkit"){
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(ModBlocks.blockMonitor);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }
    };

    public boolean isObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(boolean obfuscated) {
        this.obfuscated = obfuscated;
    }
}
