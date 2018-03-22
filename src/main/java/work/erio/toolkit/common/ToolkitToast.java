package work.erio.toolkit.common;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

/**
 * Created by Erioifpud on 2018/3/22.
 */
public class ToolkitToast implements IToast {
    private ItemStack stack;
    private Theme theme;
    private String title;
    private String subtitle;
    private long timing;
    private int titleColor;
    private int subtitleColor;
    private long firstDrawTime;
    private boolean newDisplay;

    private ToolkitToast(Theme theme, ItemStack stack, String title, String subtitle, long timing, int titleColor, int subtitleColor) {
        this.theme = theme;
        this.stack = stack;
        this.title = title;
        this.subtitle = subtitle;
        this.timing = timing;
        this.titleColor = titleColor;
        this.subtitleColor = subtitleColor;
    }

    public static Builder builder(String title) {
        return new Builder(title);
    }

    /*
    public static void addOrUpdate(GuiToast guiToast, Builder builder, Predicate<ToolkitToast> criteria) {
        ToolkitToast toast = (ToolkitToast) guiToast.getToast(ToolkitToast.class, builder.theme);
        ToolkitToast toast1 = builder.build();
        if (toast != null && criteria.test(toast1)) {
            toast.updateContent(builder);
        } else {
            guiToast.add(toast1);
        }
    }
    */

    public static void addOrUpdate(GuiToast guiToast, Builder builder) {
        ToolkitToast toast = (ToolkitToast) guiToast.getToast(ToolkitToast.class, builder.theme);
        if (toast == null) {
            guiToast.add(builder.build());
        } else {
            toast.updateContent(builder);
        }
    }

    public static void add(GuiToast guiToast, Builder builder) {
        guiToast.add(builder.build());
    }

    public ItemStack getStack() {
        return stack;
    }

    public Theme getTheme() {
        return theme;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getSubtitleColor() {
        return subtitleColor;
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        if (this.newDisplay) {
            this.firstDrawTime = delta;
            this.newDisplay = false;
        }

        GuiToast.drawRect(0, 0, 160, 32, theme.background);
        GuiToast.drawRect(1, 1, 159, 31, theme.foreground);
        if (subtitle == null) {
            toastGui.getMinecraft().fontRenderer.drawString(title, 30, 12, titleColor);
        } else {
            toastGui.getMinecraft().fontRenderer.drawString(title, 30, 7, titleColor);
            toastGui.getMinecraft().fontRenderer.drawString(subtitle, 30, 18, subtitleColor);
        }

        if (!stack.isEmpty()) {
            GuiToast.drawRect(7, 7, 25, 25, theme.background);
            RenderHelper.enableGUIStandardItemLighting();
            toastGui.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) null, stack, 8, 8);
        }
        return delta - this.firstDrawTime < this.timing ? Visibility.SHOW : Visibility.HIDE;
    }

    @Override
    public Object getType() {
        return this.theme;
    }

    public void updateContent(Builder builder) {
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.stack = builder.stack;
        this.theme = builder.theme;
        this.timing = builder.timing;
        this.titleColor = builder.titleColor;
        this.subtitleColor = builder.subtitleColor;
        this.newDisplay = true;
    }

    public enum Theme {
        SUCCESS(0xff20bf6b, 0xff26de81),
        PRIMARY(0xff3867d6, 0xff4b7bec),
        SECONDARY(0xff4b6584, 0xff778ca3),
        WARNING(0xffe1b12c, 0xfffbc531),
        DANGER(0xffeb3b5a, 0xfffc5c65);

        /*
        SUCCESS(0xff44bd32, 0xff4cd137),
        PRIMARY(0xff0097e6, 0xff00a8ff),
        SECONDARY(0xff718093, 0xff7f8fa6),
        WARNING(0xffe1b12c, 0xfffbc531),
        DANGER(0xffc23616, 0xffe84118);
        */

        private int background;
        private int foreground;

        Theme(int background, int foreground) {
            this.background = background;
            this.foreground = foreground;
        }

        public int getBackground() {
            return background;
        }

        public int getForeground() {
            return foreground;
        }
    }

    public static class Builder {
        private Theme theme = Theme.PRIMARY;
        private ItemStack stack = ItemStack.EMPTY;
        private String title;
        private String subtitle;
        private long timing = 5000;
        private int titleColor = 0xffffff;
        private int subtitleColor = 0xf1f2f6;

        private Builder(String title) {
            this.title = title;
        }

        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder setStack(ItemStack stack) {
            this.stack = stack;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setTiming(long timing) {
            this.timing = timing;
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setSubtitleColor(int subtitleColor) {
            this.subtitleColor = subtitleColor;
            return this;
        }

        public ToolkitToast build() {
            return new ToolkitToast(theme, stack, title, subtitle, timing, titleColor, subtitleColor);
        }
    }
}
