package work.erio.toolkit.block;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.common.ToolkitToast;
import work.erio.toolkit.tile.TileEntityKeypad;
import work.erio.toolkit.util.TextUtils;

import java.util.Random;

/**
 * Created by Erioifpud on 2018/3/23.
 */
public class BlockPulseBaseMonitor extends AbstractBlockMonitor {
    private boolean prevState;

    public BlockPulseBaseMonitor() {
        super();
        setUnlocalizedName(Toolkit.MODID + ".pulse_base_monitor_block");
        setRegistryName("pulse_base_monitor_block");
        prevState = false;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        onPowerChanged(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        onPowerChanged(worldIn, pos);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        onPowerChanged(worldIn, pos);
    }

    private void onPowerChanged(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            if (prevState && !worldIn.isBlockPowered(pos)) {
                getBlockData(worldIn, pos);
                prevState = false;
            } else if (!prevState && worldIn.isBlockPowered(pos)) {
                getBlockData(worldIn, pos);
                prevState = true;
            }
        }
    }

    private void printData(int value) {
        ToolkitToast.Builder builder = ToolkitToast.builder(TextUtils.getTranslation("power")).setTheme(ToolkitToast.Theme.INFO).setStack(new ItemStack(ModBlocks.blockPulseBaseMonitor)).setSubtitle(String.valueOf(value));
        ToolkitToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), builder);
    }

    private void getBlockData(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos.down());
        if (tileEntity != null) {
            tileEntity.markDirty();
        }
        int value = 0;
        IBlockState state = world.getBlockState(pos.down());
        if (tileEntity instanceof TileEntityComparator) {
            value = ((TileEntityComparator) tileEntity).getOutputSignal();
        } else if (tileEntity instanceof TileEntityKeypad) {
            value = ((TileEntityKeypad) tileEntity).getPower();
        } else if (state.getBlock() == Blocks.REDSTONE_WIRE) {
            value = Blocks.REDSTONE_WIRE.getMetaFromState(state);
        } else if (tileEntity instanceof IInventory) {
            value = Container.calcRedstone(tileEntity);
        } else if (tileEntity instanceof BlockJukebox.TileEntityJukebox) {
            ItemStack stack = ((BlockJukebox.TileEntityJukebox) tileEntity).getRecord();
            value = Item.getIdFromItem(stack.getItem()) + 1 - Item.getIdFromItem(Items.RECORD_13);
        } else if (tileEntity instanceof TileEntityDaylightDetector) {
            value = state.getValue(BlockDaylightDetector.POWER);
        } else if (state.getBlock() instanceof BlockLiquid) {
            value = state.getValue(BlockLiquid.LEVEL);
        } else if (state.getBlock() instanceof BlockPressurePlateWeighted) {
            value = state.getValue(BlockPressurePlateWeighted.POWER);
        }
        printData(value);
    }
}
