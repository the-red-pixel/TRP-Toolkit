package work.erio.toolkit.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiSwitch<T> extends GuiButton {

    protected T[] items;
    private int index;
    private String name;

//    public GuiSwitch(int buttonId, int x, int y, Enum items) {
//        this(buttonId, x, y, 60, 20, items);
//    }

    public GuiSwitch(int buttonId, int x, int y, T[] items, String name) {
        this(buttonId, x, y, 60, 20, items, name);
    }

    public GuiSwitch(int buttonId, int x, int y, int widthIn, int heightIn, T[] items, String name) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.items = items;
        this.index = 0;
        this.name = name;
//        System.out.println(Arrays.toString(this.items.getClass().getEnumConstants()));
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.items.length == 0) {
            return;
        }
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRenderer;
            int j = 14737632;
            String s;
            String currentItem = this.items[this.index].toString();
            if (this.hovered) {
                s = String.format("%s: %s", this.name, currentItem);
            } else {
                s = currentItem;
            }
            this.drawCenteredString(fontrenderer, s, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }

    public void mouseClicked(int mouseButton) {
        if (this.items.length == 0) {
            return;
        }
        if (mouseButton == 0) {
            if (this.hovered) {
                int length = this.items.length;
                this.index = this.index < length - 1 ? this.index + 1 : 0;
            }
        }
    }

    public T getSelectedItem() {
        if (this.items.length == 0) {
            return null;
        } else {
            return this.items[this.index];
        }
    }

    public void setValue(T val) {
        for (int i = 0; i < this.items.length; i++) {
            if (val.equals(this.items[i])) {
                this.index = i;
                return;
            }
        }
    }
}
