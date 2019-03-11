package work.erio.toolkit.plugin;

import work.erio.toolkit.tile.TileEntityUniversalSign;

public interface IRenderable {
    void onRender(TileEntityUniversalSign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha);

    boolean preventDefault();
}
