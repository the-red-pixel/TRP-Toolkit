package work.erio.toolkit.block;

import javafx.application.Application;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.render.RenderMonitor;
import work.erio.toolkit.tile.TileEntityMonitor;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/2.
 */
public class BlockMonitor extends Block implements ITileEntityProvider {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0f, 0f, 0f, 0f, 0f, 0f);
    private static final AxisAlignedBB SELECTED_BOUNDING_BOX = new AxisAlignedBB(0f, 0f, 0f, 1f, 0.1f, 1f);

    public BlockMonitor() {
        super(Material.GLASS);
        setUnlocalizedName(Toolkit.MODID + ".monitor_block");
        setRegistryName("monitor_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityMonitor();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMonitor.class, new RenderMonitor());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        if (!worldIn.isRemote) {
            if (heldItem.isEmpty()) {
                return false;
            } else if (heldItem.getItem() == Item.getItemFromBlock(this)){
                getTileEntity(worldIn, pos).getDataSet();
                return true;
            } else {
                getTileEntity(worldIn, pos).setStack(heldItem);
                return true;
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }


    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SELECTED_BOUNDING_BOX;
    }


    private TileEntityMonitor getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityMonitor) world.getTileEntity(pos);
    }
}
