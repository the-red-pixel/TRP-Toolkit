package work.erio.toolkit;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.item.ItemUniversalWrench;

/**
 * Created by Erioifpud on 2018/3/5.
 */
public class ModItems {
//    @GameRegistry.ObjectHolder("toolkit:universal_wrench")
    public static ItemUniversalWrench itemUniversalWrench = new ItemUniversalWrench();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemUniversalWrench.initModel();
    }
}