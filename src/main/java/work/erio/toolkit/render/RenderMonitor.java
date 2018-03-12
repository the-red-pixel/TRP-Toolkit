package work.erio.toolkit.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.tile.TileEntityMonitor;

/**
 * Created by Erioifpud on 2018/3/4.
 */
@SideOnly(Side.CLIENT)
public class RenderMonitor extends TileEntitySpecialRenderer<TileEntityMonitor> {

    @Override
    public void render(TileEntityMonitor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        drawNameplate(te, String.valueOf(te.getValue()), x, y - 1, z, 16);
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        drawItemStack(te);
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void drawItemStack(TileEntityMonitor te) {
        ItemStack stack = te.getStack();
        if (te.hasItem()) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.5f, 0.2f, 0.5f);
            GlStateManager.scale(0.4f, 0.4f, 0.4f);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
            GlStateManager.popMatrix();
        }
    }
}
