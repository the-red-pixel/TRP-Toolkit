package work.erio.toolkit.render;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import work.erio.toolkit.misc.BlockInfo;
import work.erio.toolkit.tile.TileEntityModel;

import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class RenderModel extends TileEntitySpecialRenderer<TileEntityModel> {

    @Override
    public void render(TileEntityModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
//        renderBlock3(x, y, z, 0, 0, 0, Blocks.SLIME_BLOCK);
//        renderBlock3(x, y, z, 1, 0, 0, Blocks.TNT);
//        renderBlock3(x, y, z, 0, 0, 1, Blocks.ANVIL);

//        BlockInfo[][][] blockInfos = te.getBlockInfos();
//        System.out.println(Arrays.deepToString(blockInfos));




        BlockInfo[] blockInfos = te.getBlockInfos();


//        renderBlock3(x, y, z, 0, 1, 1, blockInfos[0].getBlock());



//        for (int i = 0; i < 16; i++) {
//            for (int j = 0; j < 16; j++) {
//                for (int k = 0; k < 16; k++) {
//                    int index = i * 256 + j * 16 + k;
//                    Block block = blockInfos[index].getBlock();
//                    if (block != Blocks.AIR) {
//                        renderBlock3(x, y, z, i, j , k, block);
//                    }
//                }
//            }
//        }



//        renderBlock5(x, y, z, blockInfos);


        renderBlock2(te, x, y, z, partialTicks, destroyStage, alpha);

    }



    private void renderBlock2(TileEntityModel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 和玩家位置相对
        IBlockState blockState = Blocks.TNT.getDefaultState();
        IBlockAccess blockAccess = Minecraft.getMinecraft().world;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder wr = tessellator.getBuffer();
        final BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        for (BlockRenderLayer layer : BlockRenderLayer.values()) {
            if (blockState.getBlock().canRenderInLayer(blockState, layer)) {
                wr.setTranslation(x, y, z);
                wr.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                dispatcher.renderBlock(blockState, te.getPos(), blockAccess, wr);
                tessellator.draw();
            }
        }
    }

    private void renderBlock3(double x, double y, double z, int offsetX, int offsetY, int offsetZ, Block block) {
        Minecraft mc = Minecraft.getMinecraft();
        IBlockState blockState = block.getDefaultState();
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0, 0, 1);
        GlStateManager.translate(offsetX, offsetY, offsetZ);

        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        blockrendererdispatcher.renderBlockBrightness(blockState, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
    }

    private void renderBlock5(double x, double y, double z, BlockInfo[] blockInfos) {
        Minecraft mc = Minecraft.getMinecraft();
//        IBlockState blockState = block.getDefaultState();
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
//        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(0, 0, 1);


        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int k = 0; k < 16; k++) {
                    int index = i * 256 + j * 16 + k;
                    Block block = blockInfos[index].getBlock();
                    if (block != Blocks.AIR) {
                        GlStateManager.pushMatrix();
                        GlStateManager.enableRescaleNormal();
                        GlStateManager.translate(i, j, k);
                        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                        blockrendererdispatcher.renderBlockBrightness(block.getDefaultState(), 1.0F);
                        GlStateManager.disableRescaleNormal();
                        GlStateManager.popMatrix();
                    }
                }
            }
        }

        GlStateManager.popMatrix();

    }

    private void renderBlock4(TileEntityModel te, double x, double y, double z, int offsetX, int offsetY, int offsetZ, Block block) {
        Minecraft mc = Minecraft.getMinecraft();
        BlockPos pos = te.getPos();
        GlStateManager.enableDepth();
        BlockRendererDispatcher blockrendererdispatcher = mc.getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.scale(0.0625d, 0.0625d, 0.0625d);
//        GlStateManager.rotate(45.0F, -1.0F, 0.0F, 0.0F);
//        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0, 0, 1);
        GlStateManager.translate(offsetX, offsetY, offsetZ);

        float brightness = mc.world.getLightBrightness(pos);
        mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        blockrendererdispatcher.renderBlockBrightness(block.getDefaultState(), brightness);
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
    }

    private void drawOutline(double x, double y, double z, int offsetX, int offsetY, int offsetZ) {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableColorMaterial();
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(2);
        this.setLightmapDisabled(true);
        RenderGlobal.drawBoundingBox(x, y, z, x + offsetX, y + offsetY, z + offsetZ, 253f / 255, 201f / 255, 97 / 255, 1);
        this.setLightmapDisabled(false);
        GlStateManager.glLineWidth(1);
        GlStateManager.enableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.disableColorMaterial();
        GlStateManager.popMatrix();
    }

}
