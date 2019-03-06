package work.erio.toolkit.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import work.erio.toolkit.misc.EnumLightColor;

import javax.annotation.Nullable;

public class BlockColor implements IBlockColor, IItemColor {
    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return EnumLightColor.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
    }

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        return EnumLightColor.byMetadata(stack.getMetadata()).getColor();
    }
}
