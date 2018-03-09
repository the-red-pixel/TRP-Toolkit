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

    public RenderBox() {
        this.mc = Minecraft.getMinecraft();

    }

    @Override
    public void render(TileEntityBox te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        drawBlock(CHEST, x + 0.2D, y + 1D, z + 0.2D);
        drawBlock(DROPPER, x + 0.5D, y + 1D, z + 0.2D);
        drawBlock(SHULKER_BOX, x + 0.8D, y + 1D, z + 0.2D);

        drawBlock(DISPENSER, x + 0.2D, y + 1D, z + 0.5D);
        //drawBlock(HOPPER, x + 0.5D, y + 1D, z + 0.5D);
        drawBlock(FURNACE, x + 0.8D, y + 1D, z + 0.5D);

        drawBlock(COMMAND_BLOCK, x + 0.2D, y + 1D, z + 0.8D);
        drawBlock(HOPPER, x + 0.5D, y + 1D, z + 0.8D);
        drawBlock(JUKEBOX, x + 0.8D, y + 1D, z + 0.8D);
    }

    private void drawBlock(ItemStack itemStack, double x, double y, double z) {
        drawBlock(itemStack, x, y, z, ItemCameraTransforms.TransformType.FIXED);
    }

    private void drawBlock(ItemStack itemStack, double x, double y, double z, ItemCameraTransforms.TransformType type) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().renderItem(itemStack, type);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popAttrib();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
