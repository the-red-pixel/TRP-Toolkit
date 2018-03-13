package work.erio.toolkit;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.block.BlockBox;
import work.erio.toolkit.block.BlockKeypad;
import work.erio.toolkit.block.BlockMonitor;
import work.erio.toolkit.block.BlockRemoteSwitch;

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
    @GameRegistry.ObjectHolder("toolkit:remote_switch_block")
    public static BlockRemoteSwitch blockRemoteSwitch;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockMonitor.initModel();
        blockKeypad.initModel();
        blockBox.initModel();
        blockRemoteSwitch.initModel();
    }
}
