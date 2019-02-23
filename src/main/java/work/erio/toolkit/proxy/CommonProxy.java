package work.erio.toolkit.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
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
import work.erio.toolkit.config.Configs;
import work.erio.toolkit.handler.EventHandler;
import work.erio.toolkit.handler.GuiHandler;
import work.erio.toolkit.handler.PlayerOrderedLoadingHandler;
import work.erio.toolkit.tile.*;

import java.io.File;

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

        event.getRegistry().register(new BlockJS());
        event.getRegistry().register(new BlockPulse());



        GameRegistry.registerTileEntity(TileEntityMonitor.class, Toolkit.MODID + "_monitor");
        GameRegistry.registerTileEntity(TileEntityKeypad.class, Toolkit.MODID + "_keypad");
        GameRegistry.registerTileEntity(TileEntityBox.class, Toolkit.MODID + "_box");
        GameRegistry.registerTileEntity(TileEntityChunk.class, Toolkit.MODID + "_chunk");

        GameRegistry.registerTileEntity(TileEntityJS.class, Toolkit.MODID + "_js");
        GameRegistry.registerTileEntity(TileEntityPulse.class, Toolkit.MODID + "_pulse");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockMonitor).setRegistryName(ModBlocks.blockMonitor.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockKeypad).setRegistryName(ModBlocks.blockKeypad.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockBox).setRegistryName(ModBlocks.blockBox.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockChunk).setRegistryName(ModBlocks.blockChunk.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockPulseBaseMonitor).setRegistryName(ModBlocks.blockPulseBaseMonitor.getRegistryName()));

        event.getRegistry().register(new ItemBlock(ModBlocks.blockJS).setRegistryName(ModBlocks.blockJS.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockPulse).setRegistryName(ModBlocks.blockPulse.getRegistryName()));

        //event.getRegistry().register(new ItemScore());
    }

    public void preInit(FMLPreInitializationEvent event) {
        ForgeChunkManager.setForcedChunkLoadingCallback(Toolkit.INSTANCE, new PlayerOrderedLoadingHandler());
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Toolkit.INSTANCE, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
