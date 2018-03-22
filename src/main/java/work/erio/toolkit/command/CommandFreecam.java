package work.erio.toolkit.command;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.common.ToolkitToast;

import java.util.List;

/**
 * Created by Erioifpud on 2018/3/21.
 */
public class CommandFreecam extends CommandBase {

    @Override
    public String getName() {
        return "freecam";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/freecam - toggle free camera";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList(Toolkit.MODID, "FREECAM", "fc", "FC");
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        /*
        IToast toast = new IToast() {
            @Override
            public Visibility draw(GuiToast toastGui, long delta) {
                toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                toastGui.drawTexturedModalRect(0, 0, 0, 96, 160, 32);


                toastGui.getMinecraft().fontRenderer.drawString("title", 30, 7, -11534256);
                toastGui.getMinecraft().fontRenderer.drawString("subtitle", 30, 18, -16777216);


                Gui.drawRect(3, 28, 157, 29, -1);


                int i;
                i = -16755456;

                i = -11206656;

                double f = 0.3;
                Gui.drawRect(3, 28, (int) (3.0F + 154.0F * f), 29, i);


                return Visibility.SHOW;
            }
        };
        */
        ItemStack stack1 = new ItemStack(Item.getItemFromBlock(ModBlocks.blockMonitor));
        ItemStack stack2 = new ItemStack(Items.REDSTONE);
        ToolkitToast toast = ToolkitToast.builder("yes!").setStack(stack1).setSubtitle("subtitle").build();
        ToolkitToast toast1 = ToolkitToast.builder("title").setStack(stack2).setSubtitle("subtitle").setTheme(ToolkitToast.Theme.SUCCESS).build();
        ToolkitToast toast2 = ToolkitToast.builder("only title").setStack(stack1).setSubtitle("subtitle").setTheme(ToolkitToast.Theme.WARNING).build();
        ToolkitToast toast3 = ToolkitToast.builder("???").setStack(stack2).setSubtitle("subtitle").setTheme(ToolkitToast.Theme.DANGER).build();
        ToolkitToast toast4 = ToolkitToast.builder("secondary").setStack(stack1).setSubtitle("subtitle").setTheme(ToolkitToast.Theme.SECONDARY).build();
        Minecraft.getMinecraft().getToastGui().add(toast);
        Minecraft.getMinecraft().getToastGui().add(toast1);
        Minecraft.getMinecraft().getToastGui().add(toast2);
        Minecraft.getMinecraft().getToastGui().add(toast3);
        Minecraft.getMinecraft().getToastGui().add(toast4);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
