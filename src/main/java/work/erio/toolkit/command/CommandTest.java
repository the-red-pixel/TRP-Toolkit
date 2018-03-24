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
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.common.ToolkitToast;

import java.util.List;

/**
 * Created by Erioifpud on 2018/3/21.
 */
public class CommandTest extends CommandBase {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/test";
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList(Toolkit.MODID, "ts");
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 0) {
            execute(args[0], args);
        }
    }

    private void execute(String s, String[] args) {
        switch (s) {
            case "toast":
                testToast();
                break;

        }
    }

    private void testToast() {
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
