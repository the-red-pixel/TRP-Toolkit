package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.misc.EnumLightColor;

import java.util.Random;

public class BlockColoredLight extends Block {
    private static PropertyEnum<EnumLightColor> COLOR = PropertyEnum.create("color", EnumLightColor.class, EnumLightColor.values());
    private boolean isOn;

    public BlockColoredLight(boolean isOn) {
        super(Material.GLASS);
        this.isOn = isOn;
        setUnlocalizedName(Toolkit.MODID + ".light_block");
        setRegistryName("light_block" + (isOn ? "_on" : "_off"));
        setCreativeTab(Toolkit.TRP_TOOLKIT);

        if (isOn) {
            this.setLightLevel(1.0F);
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumLightColor lightColor : EnumLightColor.values()) {
            items.add(new ItemStack(this, 1, lightColor.getMetadata()));
        }
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.getBlockColor(EnumDyeColor.byMetadata(this.getMetaFromState(state)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, EnumLightColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || (isOn && layer == BlockRenderLayer.TRANSLUCENT);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModBlocks.blockColoredLightOff, 1, this.getMetaFromState(state));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
//            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
//                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOn.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
//            } else if (this.isOn && worldIn.isBlockPowered(pos)) {
//                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOff.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
//            }
            if (this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOff.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOn.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote) {
//            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
//                worldIn.scheduleUpdate(pos, this, 4);
//            } else if (this.isOn && worldIn.isBlockPowered(pos)) {
//                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOff.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
//            }
            if (this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.scheduleUpdate(pos, this, 0);
            } else if (!this.isOn && worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOn.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
//            if (!this.isOn && !worldIn.isBlockPowered(pos)) {
//                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOn.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
//            }
            if (this.isOn && !worldIn.isBlockPowered(pos)) {
                worldIn.setBlockState(pos, ModBlocks.blockColoredLightOff.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 2);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.blockColoredLightOff);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.blockColoredLightOff, 1, this.getMetaFromState(state));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
//        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        for (EnumLightColor lightColor : EnumLightColor.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.blockColoredLightOn), lightColor.getMetadata(), new ModelResourceLocation(ModBlocks.blockColoredLightOn.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.blockColoredLightOff), lightColor.getMetadata(), new ModelResourceLocation(ModBlocks.blockColoredLightOff.getRegistryName(), "inventory"));
        }
    }

}
