package work.erio.toolkit.command;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.common.ToolkitToast;
import work.erio.toolkit.engine.EngineManager;
import work.erio.toolkit.util.TextUtils;

import javax.script.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Erioifpud on 2018/3/21.
 */
public class CommandJS extends CommandBase {

    private ScriptEngine scriptEngine;

    @Override
    public String getName() {
        return "js";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/js <code>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("Wrong argument"));
            return;
        }

        EngineManager manager = EngineManager.getInstance();
        try {
            manager.babelEval(String.join(" ", args));
        } catch (ScriptException | IOException e) {
            e.printStackTrace();
            sender.sendMessage(new TextComponentString(e.getMessage()));
        }

    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
