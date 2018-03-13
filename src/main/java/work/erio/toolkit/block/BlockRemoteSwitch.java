package work.erio.toolkit.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.tile.TileEntityKeypad;
import work.erio.toolkit.tile.TileEntityRemoteSwitch;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public class BlockRemoteSwitch extends BlockRemote {
    public BlockRemoteSwitch() {
        super();
        setUnlocalizedName(Toolkit.MODID + ".remote_switch_block");
        setRegistryName("remote_switch_block");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRemoteSwitch();
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return getTileEntity(blockAccess, pos).isPowered() ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return getTileEntity(blockAccess, pos).isPowered() ? 15 : 0;
    }

    private TileEntityRemoteSwitch getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityRemoteSwitch) world.getTileEntity(pos);
    }
}
