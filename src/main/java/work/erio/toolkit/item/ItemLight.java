package work.erio.toolkit.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import work.erio.toolkit.misc.EnumLightColor;

public class ItemLight extends ItemBlock
{
    public ItemLight(Block block)
    {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage)
    {
        return damage;
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + EnumLightColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }
}