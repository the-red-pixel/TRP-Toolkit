package work.erio.toolkit.plugin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import work.erio.toolkit.tile.TileEntityUniversalSign;

public interface IInteractable {
    void onInteract(TileEntityUniversalSign te, ItemStack itemStack, EnumFacing facing, float hitX, float hitY, float hitZ);
}
