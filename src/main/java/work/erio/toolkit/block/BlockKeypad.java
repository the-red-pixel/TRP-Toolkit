package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.tile.TileEntityKeypad;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/5.
 */
public class BlockKeypad extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);


    public BlockKeypad() {
        super(Material.GLASS);
        setUnlocalizedName(Toolkit.MODID + ".keypad_block");
        setRegistryName("keypad_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityKeypad();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() - 2;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            // We only count on the server side.

            switch (side.getIndex()) {
                case 2:
                    click(rotate(hitX, hitY, hitZ, 3), world, pos);
                    break;
                case 3:
                    click(rotate(hitX, hitY, hitZ, 1), world, pos);
                    break;
                case 4:
                    click(rotate(hitX, hitY, hitZ, 0), world, pos);
                    break;
                case 5:
                    click(rotate(hitX, hitY, hitZ, 2), world, pos);
                    break;
                default:
                    break;
            }
        }
        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }

    private Pair<Float> rotate(float x, float y, float z, int times) {
        if (times >= 2) {
            return rotate(1 - x, y, 1 - z, times - 2);
        } else if (times == 1) {
            //return new double[] {1 - z, y, x};
            return new Pair<>(x, 1 - y);
        } else {
            return new Pair<>(z, 1 - y);
        }
    }

    private void click(Pair<Float> pos, World world, BlockPos blockPos) {
        int w = (int) ((pos.getA() - 0.125f) / 0.1875f);
        int h = (int) ((pos.getB() - 0.125f) / 0.1875f);
        TileEntityKeypad te = getTileEntity(world, blockPos);

        if (h >= 0 && h <= 2 && w >= 0 && w <= 2) {
            // key 1~9
            int number = h * 3 + (w + 1);
            te.insert(number);
        } else if (h == 3 && w >= 0 && w <= 2) {
            // key 0
            te.insert(0);
        } else if (w == 3) {
            if (h >= 0 && h <= 1) {
                // key backspace
                System.out.println("undo");
                te.undo();
            } else if (h >= 2 && h <= 3) {
                // key clear
                System.out.println("clear");
                te.clear();
            }
        }
        te.markDirty();
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return getTileEntity(worldIn, pos).getPower();
    }

    private TileEntityKeypad getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityKeypad) world.getTileEntity(pos);
    }

    class Pair<T> {
        private T a;
        private T b;

        public Pair(T a, T b) {
            this.a = a;
            this.b = b;
        }

        public T getA() {
            return a;
        }

        public T getB() {
            return b;
        }
    }
}


