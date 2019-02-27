package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.gui.GuiBlockJS;
import work.erio.toolkit.tile.TileEntityJS;

import javax.annotation.Nullable;

public class BlockJS extends BlockRedstoneDiode implements ITileEntityProvider {

    //    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockJS() {
        super(true);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                        .withProperty(POWERED, Boolean.valueOf(false))
                        .withProperty(ACTIVE, Boolean.valueOf(false)));
        setUnlocalizedName(Toolkit.MODID + ".js_block");
        setRegistryName("js_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @Override
    protected int getDelay(IBlockState state) {
        return 0;
    }

    @Override
    protected IBlockState getPoweredState(IBlockState unpoweredState) {
        return getUnpoweredState(unpoweredState);
    }

    @Override
    protected IBlockState getUnpoweredState(IBlockState poweredState) {
        Boolean active = poweredState.getValue(ACTIVE);
        Boolean powered = poweredState.getValue(POWERED);
        EnumFacing enumfacing = poweredState.getValue(FACING);
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(ACTIVE, active).withProperty(POWERED, powered);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityJS();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            getTileEntity(worldIn, pos).showGui();
//            playerIn.openGui(Toolkit.INSTANCE, GuiHandler.GUI_BLOCK_JS, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    private TileEntityJS getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityJS) world.getTileEntity(pos);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta))
                .withProperty(ACTIVE, Boolean.valueOf((meta & 8) > 0))
                .withProperty(POWERED, Boolean.valueOf((meta & 4) > 0));
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(ACTIVE)) {
            i |= 8;
        }
        if (state.getValue(POWERED)) {
            i |= 4;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, ACTIVE, POWERED});
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = this.getDefaultState();
        if (worldIn.isBlockPowered(pos)) {
            ((TileEntityJS) worldIn.getTileEntity(pos)).evalCode();
        }
        return iblockstate;
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
            boolean flag = worldIn.isBlockPowered(pos);

            if (flag || blockIn.getDefaultState().canProvidePower()) {
                ((TileEntityJS) worldIn.getTileEntity(pos)).evalCode();
            }
        }
    }
}
