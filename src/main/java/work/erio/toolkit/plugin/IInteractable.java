package work.erio.toolkit.plugin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IInteractable {
    void onInteract(ItemStack itemStack, EnumFacing facing, float hitX, float hitY, float hitZ);
}
