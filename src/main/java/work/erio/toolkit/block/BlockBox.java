package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import work.erio.toolkit.gui.GuiBlockBox;
import work.erio.toolkit.render.RenderBox;
import work.erio.toolkit.tile.TileEntityBox;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class BlockBox extends Block implements ITileEntityProvider {;
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0f, 0f, 0f, 1f, 0.5f, 1f);

    public BlockBox() {
        super(Material.GLASS);
        setUnlocalizedName(Toolkit.MODID + ".box_block");
        setRegistryName("box_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBox.class, new RenderBox());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityBox)) {
            return false;
        }
        getTileEntity(worldIn, pos).showGui();
//        playerIn.openGui(Toolkit.INSTANCE, GuiHandler.GUI_BLOCK_BOX, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
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
        return BOUNDING_BOX;
    }

    private TileEntityBox getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityBox) world.getTileEntity(pos);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBox();
    }
}
