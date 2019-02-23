package work.erio.toolkit;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.block.*;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class ModBlocks {
    @GameRegistry.ObjectHolder("toolkit:monitor_block")
    public static BlockMonitor blockMonitor;
    @GameRegistry.ObjectHolder("toolkit:keypad_block")
    public static BlockKeypad blockKeypad;
    @GameRegistry.ObjectHolder("toolkit:box_block")
    public static BlockBox blockBox;
    @GameRegistry.ObjectHolder("toolkit:chunk_block")
    public static BlockChunk blockChunk;
    @GameRegistry.ObjectHolder("toolkit:pulse_base_monitor_block")
    public static BlockPulseBaseMonitor blockPulseBaseMonitor;

    @GameRegistry.ObjectHolder("toolkit:js_block")
    public static BlockJS blockJS;
    @GameRegistry.ObjectHolder("toolkit:pulse_block")
    public static BlockPulse blockPulse;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockMonitor.initModel();
        blockKeypad.initModel();
        blockBox.initModel();
        blockChunk.initModel();
        blockPulseBaseMonitor.initModel();

        blockJS.initModel();
        blockPulse.initModel();
    }
}
