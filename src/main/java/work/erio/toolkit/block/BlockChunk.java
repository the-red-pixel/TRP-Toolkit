package work.erio.toolkit.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.render.RenderChunk;
import work.erio.toolkit.tile.TileEntityChunk;

import javax.annotation.Nullable;

/**
 * Created by Erioifpud on 2018/3/19.
 */
public class BlockChunk extends Block implements ITileEntityProvider {
    public BlockChunk() {
        super(Material.GLASS);
        setUnlocalizedName(Toolkit.MODID + ".chunk_block");
        setRegistryName("chunk_block");
        setCreativeTab(Toolkit.TRP_TOOLKIT);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChunk.class, new RenderChunk());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityChunk();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (worldIn.isRemote) {
            return;
        }
        EntityPlayer player = (EntityPlayer) placer;
        ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestPlayerTicket(Toolkit.INSTANCE, player.getName(), worldIn, ForgeChunkManager.Type.NORMAL);
        if (ticket != null) {
            TileEntityChunk te = getTileEntity(worldIn, pos);
            ticket.getModData().setTag("pos", NBTUtil.createPosTag(pos));
            te.setChunkTicket(ticket);
            te.forceChunks();
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntityChunk te = getTileEntity(worldIn, pos);
        te.clearTicketChunks();
        ForgeChunkManager.Ticket ticket = te.getTicket();
        ForgeChunkManager.releaseTicket(ticket);
    }

    private TileEntityChunk getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TileEntityChunk) world.getTileEntity(pos);
    }
}
