package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.tile.TileEntityRemoteSwitch;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public class BlockRemoteSwitch extends Block implements ITileEntityProvider {
    public BlockRemoteSwitch() {
        super(Material.GLASS);
        setUnlocalizedName(Toolkit.MODID + ".remote_switch_block");
        setRegistryName("remote_switch_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
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
