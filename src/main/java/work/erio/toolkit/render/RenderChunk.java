package work.erio.toolkit.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import work.erio.toolkit.tile.TileEntityChunk;

/**
 * Created by Erioifpud on 2018/3/20.
 */
public class RenderChunk extends TileEntitySpecialRenderer<TileEntityChunk> {
    @Override
    public void render(TileEntityChunk te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        Minecraft mc = Minecraft.getMinecraft();
        int offsetX = te.getPos().getX() % 16;
        int offsetY = te.getPos().getY() % 16;
        int offsetZ = te.getPos().getZ() % 16;
        if (mc.player.rayTrace(32, partialTicks).getBlockPos().equals(te.getPos())) {
            drawBorder(x, y, z, offsetX, offsetY, offsetZ);
        }
    }

    private void drawBorder(double x, double y, double z, int offsetX, int offsetY, int offsetZ) {
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
        RenderGlobal.drawBoundingBox(x - offsetX - 16, y, z - offsetZ - 16, x - offsetX + 32, y + 16 - offsetY, z - offsetZ + 32, 253f / 255, 201f / 255, 97 / 255, 1);
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
