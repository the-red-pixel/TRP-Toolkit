package work.erio.toolkit.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class ModelWorld extends WorldClient {
    private static final WorldSettings WORLD_SETTINGS = new WorldSettings(0, GameType.CREATIVE, false, false, WorldType.FLAT);
    private IModel model;

    public ModelWorld(IModel model) {
        super(null, WORLD_SETTINGS, 0, EnumDifficulty.PEACEFUL, Minecraft.getMinecraft().mcProfiler);
        this.model = model;
    }
}
