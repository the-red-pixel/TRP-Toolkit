package work.erio.toolkit.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Timer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import work.erio.toolkit.Toolkit;

import java.lang.reflect.Field;

public class CommandTimer extends CommandBase {
    @Override
    public String getName() {
        return "timer";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/timer <tps> or /timer default";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString("need argument"));
            return;
        }

//            Minecraft mc = Minecraft.getMinecraft();
//            Field fTimer = mc.getClass().getDeclaredField(
//                    Toolkit.INSTANCE.isObfuscated() ? "field_71428_T" : "timer");
//            fTimer.setAccessible(true);
//            Field fTickLength = Timer.class.getDeclaredField(
//                    Toolkit.INSTANCE.isObfuscated() ? "field_194149_e" : "tickLength");
//            fTickLength.setAccessible(true);
//            String arg = args[0];
//            if (arg.equals("default")) {
//                fTickLength.setFloat(fTimer.get(mc), 50);
//            } else {
//                fTickLength.setFloat(fTimer.get(mc), Float.parseFloat(arg));
//            }
            String arg = args[0];
            float tps = arg.equals("default") ? 20 : Float.parseFloat(arg);
//            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new Timer(tps), "field_71428_T");
//            ObfuscationReflectionHelper.setPrivateValue(Timer.class,
//                    ((Timer) ObfuscationReflectionHelper.getPrivateValue(
//                            Minecraft.class, Minecraft.getMinecraft(), "field_71428_T", "timer")),
//                    tick, "field_194149_e", "tickLength");

            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new Timer(tps), "field_71428_T", "timer");

    }
}
