package work.erio.toolkit.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.tile.TileEntityMonitor;

/**
 * Created by Erioifpud on 2018/3/4.
 */
@SideOnly(Side.CLIENT)
public class RenderMonitor extends TileEntitySpecialRenderer<TileEntityMonitor> {

    @Override
    public void render(TileEntityMonitor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        drawNameplate(te, String.valueOf(te.getValue()), x, y - 1, z, 16);
    }
}
