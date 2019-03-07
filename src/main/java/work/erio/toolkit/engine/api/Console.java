package work.erio.toolkit.engine.api;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import work.erio.toolkit.util.TextUtils;

public class Console {
    public static void log(String... args) {
        print(TextFormatting.WHITE, args);
    }

    public static void error(String... args) {
        print(TextFormatting.RED, args);
    }

    public static void warn(String... args) {
        print(TextFormatting.YELLOW, args);
    }

    private static void print(TextFormatting formatting, String... args) {
        Minecraft mc = Minecraft.getMinecraft();
        TextUtils.printMessage(mc.player, String.join(" ", args), formatting);
    }
}
