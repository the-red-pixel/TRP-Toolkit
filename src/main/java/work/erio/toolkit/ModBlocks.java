package work.erio.toolkit;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.block.*;

/**
 * Created by Erioifpud on 2018/3/2.
 */
@GameRegistry.ObjectHolder(Toolkit.MODID)
public class ModBlocks {
//    @GameRegistry.ObjectHolder("toolkit:monitor_block")
    public static BlockMonitor blockMonitor = new BlockMonitor();
//    @GameRegistry.ObjectHolder("toolkit:keypad_block")
    public static BlockKeypad blockKeypad = new BlockKeypad();
//    @GameRegistry.ObjectHolder("toolkit:box_block")
    public static BlockBox blockBox = new BlockBox();
//    @GameRegistry.ObjectHolder("toolkit:chunk_block")
    public static BlockChunk blockChunk = new BlockChunk();
//    @GameRegistry.ObjectHolder("toolkit:pulse_base_monitor_block")
    public static BlockPulseBaseMonitor blockPulseBaseMonitor = new BlockPulseBaseMonitor();

//    @GameRegistry.ObjectHolder("toolkit:js_block")
    public static BlockJS blockJS = new BlockJS();
//    @GameRegistry.ObjectHolder("toolkit:pulse_block")
    public static BlockPulse blockPulse = new BlockPulse();
    public static BlockSettings blockSettings = new BlockSettings();
    public static BlockModel blockModel = new BlockModel();
    public static BlockColoredLight blockColoredLightOn = new BlockColoredLight(true);
    public static BlockColoredLight blockColoredLightOff = new BlockColoredLight(false);
    public static BlockUniversalSign blockUniversalSign = new BlockUniversalSign();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockMonitor.initModel();
        blockKeypad.initModel();
        blockBox.initModel();
        blockChunk.initModel();
        blockPulseBaseMonitor.initModel();

        blockJS.initModel();
        blockPulse.initModel();
        blockSettings.initModel();
        blockModel.initModel();
        blockColoredLightOn.initModel();
        blockUniversalSign.initModel();
    }
}
