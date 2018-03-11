package work.erio.toolkit.misc;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Erioifpud on 2018/3/9.
 */
public enum EnumSupportedContainer {
    FURNACE(Blocks.FURNACE, 3),
    HOPPER(Blocks.HOPPER, 5),
    DROPPER(Blocks.DROPPER, 9),
    DISPENSER(Blocks.DISPENSER, 9),
    CHEST(Blocks.CHEST, 27),
    TRAPPED_CHEST(Blocks.TRAPPED_CHEST, 27),
    WHITE_SHULKER_BOX(Blocks.WHITE_SHULKER_BOX, 27),
    ORANGE_SHULKER_BOX(Blocks.ORANGE_SHULKER_BOX, 27),
    MAGENTA_SHULKER_BOX(Blocks.MAGENTA_SHULKER_BOX, 27),
    LIGHT_BLUE_SHULKER_BOX(Blocks.LIGHT_BLUE_SHULKER_BOX, 27),
    YELLOW_SHULKER_BOX(Blocks.YELLOW_SHULKER_BOX, 27),
    LIME_SHULKER_BOX(Blocks.LIME_SHULKER_BOX, 27),
    PINK_SHULKER_BOX(Blocks.PINK_SHULKER_BOX, 27),
    GRAY_SHULKER_BOX(Blocks.GRAY_SHULKER_BOX, 27),
    SILVER_SHULKER_BOX(Blocks.SILVER_SHULKER_BOX, 27),
    CYAN_SHULKER_BOX(Blocks.CYAN_SHULKER_BOX, 27),
    PURPLE_SHULKER_BOX(Blocks.PURPLE_SHULKER_BOX, 27),
    BLUE_SHULKER_BOX(Blocks.BLUE_SHULKER_BOX, 27),
    BROWN_SHULKER_BOX(Blocks.BROWN_SHULKER_BOX, 27),
    GREEN_SHULKER_BOX(Blocks.GREEN_SHULKER_BOX, 27),
    RED_SHULKER_BOX(Blocks.RED_SHULKER_BOX, 27),
    BLACK_SHULKER_BOX(Blocks.BLACK_SHULKER_BOX, 27);

    private Block block;
    private int slot;
    private ItemStack itemStack;


    private EnumSupportedContainer(Block block, int slot) {
        this.block = block;
        this.slot = slot;
        this.itemStack = new ItemStack(block);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Block getBlock() {
        return block;
    }

    public int getSlot() {
        return slot;
    }

    public static Optional<EnumSupportedContainer> findByUnlocalizedName(String name) {
        return Arrays.stream(values()).filter(e -> e.itemStack.getUnlocalizedName().equals(name)).findFirst();
    }
}