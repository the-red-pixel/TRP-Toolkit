package work.erio.toolkit.misc;


import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumLightColor implements IStringSerializable {
    RED(0, 15, "red", "red", 0xFFF44336),
    PINK(1, 14, "pink", "pink", 0xFFE91E63),
    PURPLE(2, 13, "purple", "purple", 0xFF9C27B0),
    DEEP_PURPLE(3, 12, "deep_purple", "deep_purple", 0xFF673AB7),
    INDIGO(4, 11, "indigo", "indigo", 0xFF3F51B5),
    BLUE(5, 10, "blue", "blue", 0xFF2196F3),
    CYAN(6, 9, "cyan", "cyan", 0xFF00BCD4),
    TEAL(7, 8, "teal", "teal", 0xFF009688),
    GREEN(8, 7, "green", "green", 0xFF4CAF50),
    LIGHT_GREEN(9, 6, "light_green", "light_green", 0xFF8BC34A),
    LIME(10, 5, "lime", "lime", 0xFFCDDC39),
    YELLOW(11, 4, "yellow", "yellow", 0xFFFFEB3B),
    AMBER(12, 3, "amber", "amber", 0xFFFFC107),
    ORANGE(13, 2, "orange", "orange", 0xFFFF9800),
    BROWN(14, 1, "brown", "brown", 0xFF795548),
    WHITE(15, 0, "white", "white", 0xFFECF0F1);

    private static final EnumLightColor[] META_LOOKUP = new EnumLightColor[values().length];
    private static final EnumLightColor[] LIGHT_DMG_LOOKUP = new EnumLightColor[values().length];
    private final int meta;
    private final int dyeDamage;
    private final String name;
    private final String unlocalizedName;
    private final int color;

    EnumLightColor(int metaIn, int dyeDamageIn, String nameIn, String unlocalizedNameIn, int color) {
        this.meta = metaIn;
        this.dyeDamage = dyeDamageIn;
        this.name = nameIn;
        this.unlocalizedName = unlocalizedNameIn;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getMetadata() {
        return this.meta;
    }

    public int getDyeDamage() {
        return this.dyeDamage;
    }

    @SideOnly(Side.CLIENT)
    public String getDyeColorName() {
        return this.name;
    }

    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    /**
     * Gets the RGB color corresponding to this dye color.
     */
//    @SideOnly(Side.CLIENT)
//    public int getColorValue() {
//        return this.colorValue;
//    }

    /**
     * Gets an array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the
     * corresponding color.
     */
//    public float[] getColorComponentValues() {
//        return this.colorComponentValues;
//    }

    public static EnumLightColor byDyeDamage(int damage) {
        if (damage < 0 || damage >= LIGHT_DMG_LOOKUP.length) {
            damage = 0;
        }

        return LIGHT_DMG_LOOKUP[damage];
    }

    public static EnumLightColor byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public String toString() {
        return this.unlocalizedName;
    }

    public String getName() {
        return this.name;
    }

    static {
        for (EnumLightColor enumLightColor : values()) {
            META_LOOKUP[enumLightColor.getMetadata()] = enumLightColor;
            LIGHT_DMG_LOOKUP[enumLightColor.getDyeDamage()] = enumLightColor;
        }
    }
}