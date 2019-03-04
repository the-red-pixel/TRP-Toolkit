package work.erio.toolkit.module;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.misc.BlockInfo;
import work.erio.toolkit.tile.TileEntityModel;
import work.erio.toolkit.util.RenderUtils;
import work.erio.toolkit.world.Model;

import java.util.HashMap;
import java.util.Map;

public class ModuleSelectModel extends AbstractModule implements IModule {
    private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0, 0, 0, 16, 16, 16);
    private Minecraft mc;
    private Model model;

    public ModuleSelectModel() {
        super(true);
        this.mc = Minecraft.getMinecraft();
        this.model = new Model();
    }

    @Override
    public String getTitle() {
        return "Select Model";
    }

    @Override
    public void onUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    }

    @Override
    public void onServerUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        if (blockState.getBlock() == ModBlocks.blockModel) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te == null) {
                return;
            }
            TileEntityModel tem = (TileEntityModel) te;
            tem.setModel(this.model);
            tem.markDirty();
        } else {
            this.saveBlocks(worldIn, pos);
        }
    }

    private void saveBlocks(World worldIn, BlockPos pos) {
        Model model = new Model();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    BlockPos relativePos = new BlockPos(i, j, k);
                    IBlockState blockState = worldIn.getBlockState(pos.add(relativePos));
                    if (blockState.getBlock() != Blocks.AIR) {
                        model.setBlockState(relativePos, blockState);
                    }
                }
            }
        }
        this.model = model;
    }

    @Override
    public void onRender(RenderWorldLastEvent evt) {
        RenderManager renderManager = this.mc.getRenderManager();
        RayTraceResult rayTraceResult = Minecraft.getMinecraft().objectMouseOver;
        if (rayTraceResult.entityHit != null) {
            return;
        }
        BlockPos pos = rayTraceResult.getBlockPos();
        if (mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
            return;
        }

        RenderUtils.drawSolidBox(renderManager, DEFAULT_AABB, pos);
    }
}
