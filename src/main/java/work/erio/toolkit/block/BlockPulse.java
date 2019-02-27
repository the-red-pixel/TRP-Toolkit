package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.ModItems;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.gui.GuiBlockPulse;
import work.erio.toolkit.tile.TileEntityPulse;

import javax.annotation.Nullable;

public class BlockPulse extends Block implements ITileEntityProvider {
//    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockPulse() {
        super(Material.GROUND);
        setUnlocalizedName(Toolkit.MODID + ".pulse_block");
        setRegistryName("pulse_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
//        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
//    }
//
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(FACING).getIndex() - 2;
//    }
//
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, FACING);
//    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return getTileEntity(worldIn, pos).getPower();
    }

//    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
//    {
//        return getTileEntity(blockAccess, pos).getPower();
//    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPulse();
    }

    private TileEntityPulse getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityPulse) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (playerIn.getHeldItemMainhand().getItem() == ModItems.itemUniversalWrench) {
                getTileEntity(worldIn, pos).toggleRunning();
            } else {
                getTileEntity(worldIn, pos).showGui();
            }
        }
        return true;
    }

//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
//        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
//    }

//    @Override
//    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
//        if (!worldIn.isRemote) {
//            if (worldIn.isBlockPowered(pos)) {
//                System.out.println(state);
//            }
//        }
//    }
}
