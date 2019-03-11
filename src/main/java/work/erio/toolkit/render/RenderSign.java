package work.erio.toolkit.render;

import net.minecraft.block.BlockLever;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.text.ITextComponent;
import work.erio.toolkit.plugin.AbstractPlugin;
import work.erio.toolkit.plugin.IRenderable;
import work.erio.toolkit.plugin.PluginManager;
import work.erio.toolkit.tile.TileEntityUniversalSign;

import java.util.List;

public class RenderSign extends TileEntitySpecialRenderer<TileEntityUniversalSign> {

    public RenderSign() {
    }

    @Override
    public void render(TileEntityUniversalSign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        AbstractPlugin plugin = te.getPlugin();
        if (plugin instanceof IRenderable) {
            IRenderable renderable = ((IRenderable) plugin);
            renderable.onRender(te, x, y, z, partialTicks, destroyStage, alpha);
            if (renderable.preventDefault()) {
                return;
            }
        }
        drawText(te, x, y, z, destroyStage);
    }

    private void drawText(TileEntityUniversalSign te, double x, double y, double z, int destroyStage) {
        int meta = te.getBlockMetadata();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        switch (BlockLever.EnumOrientation.byMetadata(meta)) {
            case NORTH:
                GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
                break;
            case EAST:
                GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F);
                break;
            case SOUTH:
                GlStateManager.rotate(0, 0.0F, 1.0F, 0.0F);
                break;
            case WEST:
                GlStateManager.rotate(-90, 0.0F, 1.0F, 0.0F);
                break;

        }
        GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
        GlStateManager.enableRescaleNormal();
        FontRenderer fontrenderer = this.getFontRenderer();
        GlStateManager.translate(0.0F, 0.33333334F, 0.046666667F);
        GlStateManager.scale(0.010416667F, -0.010416667F, 0.010416667F);
        GlStateManager.glNormal3f(0.0F, 0.0F, -0.010416667F);
        GlStateManager.depthMask(false);
        if (destroyStage < 0) {
            for (int j = 0; j < te.getSignText().length; ++j) {
                if (te.getSignText()[j] != null) {
                    ITextComponent itextcomponent = te.getSignText()[j];
                    List<ITextComponent> list = GuiUtilRenderComponents.splitText(itextcomponent, 90, fontrenderer, false, true);
                    String s = list != null && !list.isEmpty() ? ((ITextComponent) list.get(0)).getFormattedText() : "";
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.getSignText().length * 5, 0);
                }
            }
        }
        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
