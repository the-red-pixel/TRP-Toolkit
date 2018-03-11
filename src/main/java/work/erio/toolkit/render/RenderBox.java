package work.erio.toolkit.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import work.erio.toolkit.tile.TileEntityBox;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class RenderBox extends TileEntitySpecialRenderer<TileEntityBox> {
    private static final ItemStack CHEST = new ItemStack(Blocks.CHEST);
    private static final ItemStack SHULKER_BOX = new ItemStack(Blocks.YELLOW_SHULKER_BOX);
    private static final ItemStack DROPPER = new ItemStack(Blocks.DROPPER);
    private static final ItemStack DISPENSER = new ItemStack(Blocks.DISPENSER);
    private static final ItemStack HOPPER = new ItemStack(Blocks.HOPPER);
    private static final ItemStack FURNACE = new ItemStack(Blocks.FURNACE);
    private static final ItemStack CAULDRON = new ItemStack(Blocks.CAULDRON);
    private static final ItemStack COMMAND_BLOCK = new ItemStack(Blocks.COMMAND_BLOCK);
    private static final ItemStack JUKEBOX = new ItemStack(Blocks.JUKEBOX);
    private Minecraft mc;
    private float actualVal;

    public RenderBox() {
        this.mc = Minecraft.getMinecraft();
        this.actualVal = 0;
    }

    @Override
    public void render(TileEntityBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
         actualVal = te.getAngle() + (Math.abs(te.getAngle() - te.getOldAngle())) * partialTicks;
         drawAll(x, y, z, actualVal);
    }

    private void drawAll(double x, double y, double z, float angle) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
        GlStateManager.disableLighting();
        GlStateManager.rotate(angle, 0.0F, 1.0F, 0.0F);
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        drawBlock(CHEST, -0.3D, 0.5D, -0.3D);
        drawBlock(DROPPER, 0D, 0.5D, -0.3D);
        drawBlock(SHULKER_BOX, 0.3D, 0.5D, -0.3D);
        drawBlock(DISPENSER, -0.3D, 0.5D, 0D);
        drawBlock(FURNACE, 0.3D, 0.5D, 0D);
        drawBlock(COMMAND_BLOCK, -0.3, 0.5D, 0.3D);
        drawBlock(HOPPER, 0D, 0.5D, 0.3D);
        drawBlock(JUKEBOX, 0.3D, 0.5D, 0.3D);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void drawBlock(ItemStack itemStack, double x, double y, double z) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().renderItem(itemStack, ItemCameraTransforms.TransformType.FIXED);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
