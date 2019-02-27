package work.erio.toolkit.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public class TextUtils {
    @SideOnly(Side.CLIENT)
    public static String getTranslationKey(String key) {
        return String.format("message.%s.%s", Toolkit.MODID, key);
    }

    @SideOnly(Side.CLIENT)
    public static String getTranslation(String key, Object... obj) {

        return I18n.format(getTranslationKey(key), obj);
    }

    @SideOnly(Side.CLIENT)
    public static void printTranslation(EntityPlayer player, String key, TextFormatting color) {
        TextComponentTranslation message = new TextComponentTranslation(getTranslationKey(key));
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

    @SideOnly(Side.CLIENT)
    public static void printTranslation(EntityPlayer player, String key) {
        printTranslation(player, key, TextFormatting.WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void printTranslationFormat(EntityPlayer player, String key, TextFormatting color, Object... objects) {
        TextComponentTranslation message = new TextComponentTranslation(getTranslationKey(key), objects);
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

    @SideOnly(Side.CLIENT)
    public static void printMessage(EntityPlayer player, String s, TextFormatting color) {
        TextComponentString message = new TextComponentString(s);
        message.getStyle().setColor(color);
        player.sendStatusMessage(message, false);
    }

}
