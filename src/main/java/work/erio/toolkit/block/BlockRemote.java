package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;

/**
 * Created by Erioifpud on 2018/3/12.
 */
public abstract class BlockRemote extends Block implements ITileEntityProvider {
    public BlockRemote() {
        super(Material.GLASS);
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }
}
