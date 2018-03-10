package work.erio.toolkit.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.input.Mouse;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Erioifpud on 2018/3/9.
 */
public class GuiItemSelect extends Gui {
    public static final int WIDTH = 18;
    public static final int HEIGHT = 18;
    public static final int COLOR_BACKGROUND = 0xFFBDC3C7;
    public static final int COLOR_FOREGROUND = 0xFFECF0F1;
    private static Minecraft mc = Minecraft.getMinecraft();
    private GuiScreen parent;
    private ITextComponent title;
    private int x;
    private int y;
    private int currentIndex;
    private List<ItemStack> itemStacks;

    public GuiItemSelect(GuiScreen parent, ITextComponent title, int x, int y, Predicate<ItemStack> filter) {
        this.itemStacks = ForgeRegistries.ITEMS.getValuesCollection().parallelStream().map(ItemStack::new).filter(filter).collect(Collectors.toList());
        this.parent = parent;
        this.title = title;
        this.x = x;
        this.y = y;
        this.currentIndex = 0;
    }

    public void draw(int mouseX, int mouseY) {
        drawFrame();
        drawItems(x, y);
        drawInfo(mouseX, mouseY);
    }

    private void drawInfo(int mouseX, int mouseY) {
        if (isMouseOver()) {
            String textProgress = String.format("%d/%d", currentIndex + 1, itemStacks.size());
            GuiUtils.drawHoveringText(Arrays.asList(title.getFormattedText(), textProgress), mouseX, mouseY, parent.width, parent.height, 300, mc.fontRenderer);
        }
    }

    public void onWheelChanged() {
        if (isMouseOver()) {
            switchItem();
        }
    }

    private void switchItem() {
        int dWheel = Integer.signum(Mouse.getEventDWheel());
        if (dWheel < 0) {
            currentIndex -= currentIndex > 0 ? 1 : 0;
        } else if (dWheel > 0) {
            currentIndex += currentIndex < itemStacks.size() - 1 ? 1 : 0;
        }
    }

    private void drawItem(ItemStack stack, int x, int y) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        Optional.ofNullable(stack).ifPresent(s -> mc.getRenderItem().renderItemIntoGUI(stack, x + 1, y + 1));
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    private void drawItems(int x, int y) {
        if (!itemStacks.isEmpty()) {
            if (currentIndex != 0) {
                drawItem(itemStacks.get(currentIndex - 1), x - 20, y);
            }
            if (currentIndex != itemStacks.size() - 1) {
                drawItem(itemStacks.get(currentIndex + 1), x + 20, y);
            }
            drawItem(itemStacks.get(currentIndex), x, y);
        }
    }

    private void drawFrame() {
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        drawRect(x, y, x + WIDTH, y + HEIGHT, COLOR_BACKGROUND);
        drawRect(x + 1, y + 1, x + WIDTH - 1, y + HEIGHT - 1, COLOR_FOREGROUND);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableRescaleNormal();
    }

    private boolean isMouseOver() {
        int mouseX = Mouse.getEventX() * parent.width / mc.displayWidth;
        int mouseY = parent.height - Mouse.getEventY() * parent.height / mc.displayHeight - 1;
        return mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEIGHT;
    }

    public ItemStack getItemStack() {
        if (itemStacks.isEmpty()) {
            return null;
        }
        return itemStacks.get(currentIndex);
    }
}
