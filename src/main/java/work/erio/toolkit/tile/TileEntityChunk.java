package work.erio.toolkit.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;
import work.erio.toolkit.ModBlocks;
import work.erio.toolkit.common.ToolkitToast;
import work.erio.toolkit.util.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Erioifpud on 2018/3/19.
 */
public class TileEntityChunk extends TileEntity {
    private ForgeChunkManager.Ticket ticket;
    private ToolkitToast.Builder forceBuilder;
    private ToolkitToast.Builder unforceBuilder;

    public TileEntityChunk() {
        forceBuilder = ToolkitToast.builder(TextUtils.getTranslation("force_load")).setTheme(ToolkitToast.Theme.WARNING).setStack(new ItemStack(ModBlocks.blockChunk));
        unforceBuilder = ToolkitToast.builder(TextUtils.getTranslation("unforce_load")).setTheme(ToolkitToast.Theme.SUCCESS).setStack(new ItemStack(ModBlocks.blockChunk));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return compound;
    }

    public void setChunkTicket(ForgeChunkManager.Ticket ticket) {
        this.ticket = ticket;
    }

    public void forceChunks() {
        if (ticket == null) {
            return;
        }

        for (ChunkPos chunk : getNearChunks(1)) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player != null) {
                forceBuilder.setSubtitle(chunk.toString());
                ToolkitToast.add(mc.getToastGui(), forceBuilder);
            }
            ForgeChunkManager.forceChunk(ticket, chunk);
        }
    }

    public void clearTicketChunks() {
        if (ticket == null) {
            return;
        }

        for (ChunkPos chunk : ticket.getChunkList()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player != null) {
                unforceBuilder.setSubtitle(chunk.toString());
                ToolkitToast.add(mc.getToastGui(), unforceBuilder);
            }
            ForgeChunkManager.unforceChunk(ticket, chunk);
        }
    }

    public ForgeChunkManager.Ticket getTicket() {
        return ticket;
    }

    public void setTicket(ForgeChunkManager.Ticket ticket) {
        this.ticket = ticket;
    }

    private Set<ChunkPos> getNearChunks(int radius) {
        Set<ChunkPos> chunks = new HashSet<>();
        ChunkPos center = world.getChunkFromBlockCoords(pos).getPos();
        for (int x = center.x - radius; x <= center.x + radius; x++) {
            for (int z = center.z - radius; z <= center.z + radius; z++) {
                chunks.add(new ChunkPos(x, z));
            }
        }
        return chunks;
    }

    /*
    public ChunkSlotHandler getStackHandler() {
        return stackHandler;
    }
    */

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5)) <= 64;
    }
}
