package work.erio.toolkit.module;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import work.erio.toolkit.util.TextUtils;

public class ModuleComparator extends AbstractModule implements IModule {

    public ModuleComparator() {
        super(true);
    }

    @Override
    public String getTitle() {
        return "Comparator";
    }

    @Override
    public void onUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    }

    @Override
    public void onServerUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityComparator) {
            tile.markDirty();
            TextUtils.printMessage(player, String.valueOf(((TileEntityComparator) tile).getOutputSignal()), TextFormatting.WHITE);
        }
    }
}
