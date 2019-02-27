package work.erio.toolkit.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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
import work.erio.toolkit.ModItems;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.block.*;
import work.erio.toolkit.handler.EventHandler;
import work.erio.toolkit.handler.GuiHandler;
import work.erio.toolkit.handler.PlayerOrderedLoadingHandler;
import work.erio.toolkit.item.ItemUniversalWrench;
import work.erio.toolkit.tile.*;

import java.util.Arrays;

/**
 * Created by Erioifpud on 2018/3/2.
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
//        event.getRegistry().registerAll(
//                new BlockMonitor(),
//                new BlockKeypad(),
//                new BlockBox(),
//                new BlockChunk(),
//                new BlockPulseBaseMonitor(),
//                new BlockJS(),
//                new BlockPulse()
//        );

        event.getRegistry().registerAll(
                ModBlocks.blockMonitor,
                ModBlocks.blockKeypad,
                ModBlocks.blockBox,
                ModBlocks.blockChunk,
                ModBlocks.blockPulseBaseMonitor,
                ModBlocks.blockJS,
                ModBlocks.blockPulse,
                ModBlocks.blockSettings
        );

        GameRegistry.registerTileEntity(TileEntityMonitor.class, new ResourceLocation(Toolkit.MODID, "monitor"));
        GameRegistry.registerTileEntity(TileEntityKeypad.class, new ResourceLocation(Toolkit.MODID, "keypad"));
        GameRegistry.registerTileEntity(TileEntityBox.class, new ResourceLocation(Toolkit.MODID, "box"));
        GameRegistry.registerTileEntity(TileEntityChunk.class, new ResourceLocation(Toolkit.MODID, "chunk"));

        GameRegistry.registerTileEntity(TileEntityJS.class, new ResourceLocation(Toolkit.MODID, "js"));
        GameRegistry.registerTileEntity(TileEntityPulse.class, new ResourceLocation(Toolkit.MODID, "pulse"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockMonitor).setRegistryName(ModBlocks.blockMonitor.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockKeypad).setRegistryName(ModBlocks.blockKeypad.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockBox).setRegistryName(ModBlocks.blockBox.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockChunk).setRegistryName(ModBlocks.blockChunk.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockPulseBaseMonitor).setRegistryName(ModBlocks.blockPulseBaseMonitor.getRegistryName()));
//
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockJS).setRegistryName(ModBlocks.blockJS.getRegistryName()));
//        event.getRegistry().register(new ItemBlock(ModBlocks.blockPulse).setRegistryName(ModBlocks.blockPulse.getRegistryName()));

        event.getRegistry().registerAll(itemBlocksWrapper(
                ModBlocks.blockMonitor,
                ModBlocks.blockKeypad,
                ModBlocks.blockBox,
                ModBlocks.blockChunk,
                ModBlocks.blockPulseBaseMonitor,
                ModBlocks.blockJS,
                ModBlocks.blockPulse,
                ModBlocks.blockSettings
        ));

        event.getRegistry().register(ModItems.itemUniversalWrench);
    }

    private static Item[] itemBlocksWrapper(Block... block) {
        return Arrays.stream(block).map(b -> new ItemBlock(b).setRegistryName(b.getRegistryName())).toArray(Item[]::new);
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
