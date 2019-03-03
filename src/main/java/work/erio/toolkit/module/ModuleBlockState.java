package work.erio.toolkit.module;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import work.erio.toolkit.util.TextUtils;

public class ModuleBlockState extends AbstractModule implements IModule {
    public ModuleBlockState() {
        super(true);
    }

    @Override
    public String getTitle() {
        return "BlockState";
    }

    @Override
    public void onUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        TextUtils.printMessage(player, pos + "", TextFormatting.DARK_AQUA);
        TextUtils.printMessage(player, blockState + "", TextFormatting.GREEN);
        TextUtils.printMessage(player, blockState.getProperties() + "", TextFormatting.GOLD);
        TextUtils.printMessage(player, blockState.getBlock().getMetaFromState(blockState) + "", TextFormatting.WHITE);

//        worldIn
    }

    @Override
    public void onServerUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {


    }

    @Override
    public void onRender(RenderWorldLastEvent evt) {

    }
}
