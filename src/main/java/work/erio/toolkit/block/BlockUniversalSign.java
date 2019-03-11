package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import work.erio.toolkit.plugin.AbstractPlugin;
import work.erio.toolkit.plugin.IInteractable;
import work.erio.toolkit.plugin.IPowerable;
import work.erio.toolkit.plugin.IUpdatable;
import work.erio.toolkit.render.RenderSign;
import work.erio.toolkit.tile.TileEntityUniversalSign;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockUniversalSign extends Block implements ITileEntityProvider {
    public static final PropertyEnum<BlockLever.EnumOrientation> FACING = PropertyEnum.<BlockLever.EnumOrientation>create("facing", BlockLever.EnumOrientation.class);
    //    protected static final AxisAlignedBB SIGN_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
    protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0D, 0.90625D, 0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.09375D, 1.0D);
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.90625D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.09375D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.90625D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.09375D, 1.0D, 1.0D);

    public BlockUniversalSign() {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, BlockLever.EnumOrientation.NORTH));
        setUnlocalizedName(Toolkit.MODID + ".sign_block");
        setRegistryName("sign_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        BlockLever.EnumOrientation enumfacing = (BlockLever.EnumOrientation) state.getValue(FACING);

        switch (enumfacing) {
            case UP_X:
                return AABB_UP;
            case UP_Z:
                return AABB_UP;
            case DOWN_X:
                return AABB_DOWN;
            case DOWN_Z:
                return AABB_DOWN;
            case NORTH:
                return AABB_NORTH;
            case SOUTH:
                return AABB_SOUTH;
            case WEST:
                return AABB_WEST;
            case EAST:
                return AABB_EAST;
            default:
                return AABB_UP;
        }
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canSpawnInBlock() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityUniversalSign();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.SIGN;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Items.SIGN);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!isCorrectTileEntity(pos)) {
            return false;
        }

        if (playerIn.isSneaking()) {
            getTileEntity(worldIn, pos).showGui();
        } else {
            AbstractPlugin plugin = getTileEntity(worldIn, pos).getPlugin();
            if (plugin instanceof IInteractable) {
                ((IInteractable) plugin).onInteract(playerIn.getHeldItem(hand), facing, hitX, hitY, hitZ);
            }
        }
        return true;
    }

    private TileEntityUniversalSign getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityUniversalSign) world.getTileEntity(pos);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, BlockLever.EnumOrientation.byMetadata(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((BlockLever.EnumOrientation) state.getValue(FACING)).getMetadata();
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUniversalSign.class, new RenderSign());
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = this.getDefaultState();
        return iblockstate.withProperty(FACING, BlockLever.EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    private boolean isCorrectTileEntity(BlockPos pos) {
        TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
        return te instanceof TileEntityUniversalSign;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        if (!isCorrectTileEntity(pos)) {
            return 0;
        }

        AbstractPlugin plugin = getTileEntity(worldIn, pos).getPlugin();
        if (plugin instanceof IPowerable) {
            return ((IPowerable) plugin).getComparatorPower();
        } else {
            return 0;
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!isCorrectTileEntity(pos)) {
            return;
        }

        AbstractPlugin plugin = getTileEntity(worldIn, pos).getPlugin();
        if (plugin instanceof IUpdatable) {
            ((IUpdatable) plugin).onNeighborUpdate(worldIn, fromPos, pos);
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        if (!isCorrectTileEntity(pos)) {
            return;
        }

        AbstractPlugin plugin = getTileEntity(world, pos).getPlugin();
        if (plugin instanceof IUpdatable) {
            ((IUpdatable) plugin).onNeighborTileUpdate(world, neighbor, pos);
        }
    }
}
