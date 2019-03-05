package work.erio.toolkit.render;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.misc.BlockInfo;
import work.erio.toolkit.tile.TileEntityModel;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class RenderModel extends TileEntitySpecialRenderer<TileEntityModel> {

    @Override
    public void render(TileEntityModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        Map<BlockPos, BlockInfo> blockInfos = te.getBlockInfos();
        renderBlocks(x, y, z, blockInfos);
    }

//    private void renderBlock3(double x, double y, double z, int offsetX, int offsetY, int offsetZ, BlockInfo blockInfo) {
//        Minecraft mc = Minecraft.getMinecraft();
//        int meta = blockInfo.getMeta();
//        Block block = blockInfo.getBlock();
//        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
//        GlStateManager.enableRescaleNormal();
//        GlStateManager.pushMatrix();
//        GlStateManager.translate(x, y, z);
//        GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        GlStateManager.translate(0, 0, 1);
//        GlStateManager.translate(offsetX, offsetY, offsetZ);
//
//        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//
//        blockrendererdispatcher.renderBlockBrightness(block.getStateFromMeta(meta), 1.0F);
//        GlStateManager.popMatrix();
//        GlStateManager.disableRescaleNormal();
//    }

    private void renderBlocks(double x, double y, double z, Map<BlockPos, BlockInfo> blockInfos) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0, 0, 1);

        for (Map.Entry<BlockPos, BlockInfo> pair : blockInfos.entrySet()) {
            BlockPos pos = pair.getKey();
            BlockInfo blockInfo = pair.getValue();
            int meta = blockInfo.getMeta();
            Block block = blockInfo.getBlock();
            IBlockState blockState = block.getStateFromMeta(meta);
            EnumBlockRenderType enumblockrendertype = blockState.getRenderType();

            int oX = pos.getX();
            int oY = pos.getY();
            int oZ = pos.getZ();
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translate(oX, oY, oZ);
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            if (enumblockrendertype.equals(EnumBlockRenderType.LIQUID)) {

            } else {
                blockrendererdispatcher.renderBlockBrightness(blockState, 1.0F);
            }

            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();

    }

}
