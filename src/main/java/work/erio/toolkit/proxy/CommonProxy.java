package work.erio.toolkit.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.block.*;
import work.erio.toolkit.handler.EventHandler;
import work.erio.toolkit.handler.GuiHandler;
import work.erio.toolkit.handler.PlayerOrderedLoadingHandler;
import work.erio.toolkit.tile.TileEntityBox;
import work.erio.toolkit.tile.TileEntityChunk;
import work.erio.toolkit.tile.TileEntityKeypad;
import work.erio.toolkit.tile.TileEntityMonitor;

/**
 * Created by Erioifpud on 2018/3/2.
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockMonitor());
        event.getRegistry().register(new BlockKeypad());
        event.getRegistry().register(new BlockBox());
        event.getRegistry().register(new BlockChunk());
        event.getRegistry().register(new BlockPulseBaseMonitor());



        GameRegistry.registerTileEntity(TileEntityMonitor.class, Toolkit.MODID + "_monitor");
        GameRegistry.registerTileEntity(TileEntityKeypad.class, Toolkit.MODID + "_keypad");
        GameRegistry.registerTileEntity(TileEntityBox.class, Toolkit.MODID + "_box");
        GameRegistry.registerTileEntity(TileEntityChunk.class, Toolkit.MODID + "_chunk");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockMonitor).setRegistryName(ModBlocks.blockMonitor.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockKeypad).setRegistryName(ModBlocks.blockKeypad.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockBox).setRegistryName(ModBlocks.blockBox.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockChunk).setRegistryName(ModBlocks.blockChunk.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockPulseBaseMonitor).setRegistryName(ModBlocks.blockPulseBaseMonitor.getRegistryName()));

        //event.getRegistry().register(new ItemScore());
    }

    public void preInit(FMLPreInitializationEvent event) {
        ForgeChunkManager.setForcedChunkLoadingCallback(Toolkit.instance, new PlayerOrderedLoadingHandler());
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Toolkit.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
