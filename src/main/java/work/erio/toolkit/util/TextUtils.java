package work.erio.toolkit.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import work.erio.toolkit.Toolkit;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public class TextUtils {
    public static String getTranslationKey(String key) {
        return String.format("message.%s.%s", Toolkit.MODID, key);
    }

    public static void printTranslation(EntityPlayer player, String key, TextFormatting color) {
        TextComponentTranslation message = new TextComponentTranslation(getTranslationKey(key));
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

    public static void printTranslation(EntityPlayer player, String key) {
        printTranslation(player, key, TextFormatting.WHITE);
    }

    public static void printTranslationFormat(EntityPlayer player, String key, TextFormatting color, Object... objects) {
        TextComponentTranslation message = new TextComponentTranslation(getTranslationKey(key), objects);
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

    public static void printMessage(EntityPlayer player, String s, TextFormatting color) {
        TextComponentString message = new TextComponentString(s);
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

}
