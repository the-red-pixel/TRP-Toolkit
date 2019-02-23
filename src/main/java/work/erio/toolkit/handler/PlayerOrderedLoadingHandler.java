package work.erio.toolkit.handler;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import work.erio.toolkit.tile.TileEntityChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erioifpud on 2018/3/19.
 */
public class PlayerOrderedLoadingHandler implements ForgeChunkManager.PlayerOrderedLoadingCallback {

    @Override
    public ListMultimap<String, ForgeChunkManager.Ticket> playerTicketsLoaded(ListMultimap<String, ForgeChunkManager.Ticket> tickets, World world) {
        ListMultimap<String, ForgeChunkManager.Ticket> validTickets = ArrayListMultimap.create();
        for (String playerName : tickets.keySet()) {
            List<ForgeChunkManager.Ticket> playerTickets = new ArrayList<>();

            for (ForgeChunkManager.Ticket tkt : tickets.get(playerName)) {
                BlockPos ticketPosition = NBTUtil.getPosFromTag(tkt.getModData().getCompoundTag("pos"));
                TileEntity te = world.getTileEntity(ticketPosition);
                if (te instanceof TileEntityChunk) {
                    playerTickets.add(tkt);
                }
            }
            validTickets.putAll(playerName, playerTickets);
        }
        return validTickets;
    }

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        for (ForgeChunkManager.Ticket ticket : tickets) {
            BlockPos ticketPosition = NBTUtil.getPosFromTag(ticket.getModData().getCompoundTag("pos"));
            TileEntity te = world.getTileEntity(ticketPosition);
            if (te instanceof TileEntityChunk) {
                TileEntityChunk loader = (TileEntityChunk) te;
                loader.setChunkTicket(ticket);
                loader.forceChunks();
            } else {
                ForgeChunkManager.releaseTicket(ticket);
            }
        }
    }
}
