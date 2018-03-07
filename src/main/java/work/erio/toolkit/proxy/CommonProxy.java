package work.erio.toolkit.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.block.BlockKeypad;
import work.erio.toolkit.block.BlockMonitor;
import work.erio.toolkit.tile.TileEntityKeypad;
import work.erio.toolkit.tile.TileEntityMonitor;

/**
 * Created by Erioifpud on 2018/3/2.
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockMonitor());
        event.getRegistry().register(new BlockKeypad());

        GameRegistry.registerTileEntity(TileEntityMonitor.class, Toolkit.MODID + "_monitor");
        GameRegistry.registerTileEntity(TileEntityKeypad.class, Toolkit.MODID + "_keypad");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockMonitor).setRegistryName(ModBlocks.blockMonitor.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockKeypad).setRegistryName(ModBlocks.blockKeypad.getRegistryName()));
    }
}
