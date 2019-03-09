package work.erio.toolkit.tile;

import com.rabbit.gui.RabbitGui;
import net.minecraft.block.BlockLever;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import work.erio.toolkit.gui.GuiBlockSign;

public class TileEntityUniversalSign extends TileEntity {
    private String pluginName;
    private ITextComponent[] signText = new ITextComponent[]{new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};

    public TileEntityUniversalSign() {
        this.pluginName = "";
        signText[0] = new TextComponentString("Test");
    }

    public void showGui() {
        RabbitGui.proxy.display(new GuiBlockSign(this));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("plugin", pluginName);
        for (int i = 0; i < 4; ++i) {
            String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
            compound.setString("Text" + (i + 1), s);
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.pluginName = compound.getString("plugin");
        for (int i = 0; i < 4; ++i) {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.jsonToComponent(s);
            this.signText[i] = itextcomponent;
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    public ITextComponent[] getSignText() {
        return signText;
    }

    public BlockPos getCloseBlockPos() {
        switch (BlockLever.EnumOrientation.byMetadata(this.getBlockMetadata())) {
            case UP_X:
                return this.getPos().up();
            case UP_Z:
                return this.getPos().up();
            case DOWN_X:
                return this.getPos().down();
            case DOWN_Z:
                return this.getPos().down();
            case NORTH:
                return this.getPos().south();
            case SOUTH:
                return this.getPos().north();
            case WEST:
                return this.getPos().east();
            case EAST:
                return this.getPos().west();
            default:
                return this.getPos().down();
        }
    }
}
