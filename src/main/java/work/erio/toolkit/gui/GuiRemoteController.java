package work.erio.toolkit.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.tile.TileEntityRemoteSwitch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erioifpud on 2018/3/13.
 */
public class GuiRemoteController extends GuiScreen {
    private ItemStack stack;
    private List<GuiRemoteNode> nodeList;
    //private GuiRemoteNode node;

    public GuiRemoteController(ItemStack stack) {
        this.stack = stack;
        this.nodeList = new ArrayList<>();
        //node = new GuiRemoteNode(this);
        initNodes();
    }

    private void initNodes() {
        NBTTagCompound root = stack.getTagCompound();
        if (root != null && root.hasKey("posList")) {
            NBTTagList posListTag = root.getTagList("posList", Constants.NBT.TAG_COMPOUND);
            if (!posListTag.hasNoTags()) {
                //Iterator<NBTBase> iterater = posListTag.iterator();
                for (int i = 0; i < posListTag.tagCount(); i++) {
                    NBTTagCompound compound = posListTag.getCompoundTagAt(i);
                    BlockPos pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
                    TileEntity tileEntity = Minecraft.getMinecraft().world.getTileEntity(pos);
                    if (tileEntity instanceof TileEntityRemoteSwitch) {
                        nodeList.add(new GuiRemoteNode(this, pos));
                    } else {
                        posListTag.removeTag(i);
                    }

                }
                /*
                NBTTagCompound posCompound = posListTag.getCompoundTagAt(0);
                BlockPos pos = new BlockPos(posCompound.getInteger("x"), posCompound.getInteger("y"), posCompound.getInteger("z"));
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof TileEntityRemoteSwitch) {
                    TileEntityRemoteSwitch te = (TileEntityRemoteSwitch) tileEntity;

                    te.setPowered(!te.isPowered());
                    System.out.println(te.isPowered());
                }
                */
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        nodeList.forEach(n -> n.draw(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        /*
        for (int i = 0; i < nodeList.size(); i++) {
            GuiRemoteNode n = nodeList.get(i);
            if (n.isOverOther(mouseX, mouseY)) {
                if (n.isOnTop()) {
                    n.mouseClicked(mouseX, mouseY, mouseButton);
                } else {
                    nodeList.remove(n);
                    nodeList.add(n);
                }
            }
        }       OK de
        */
        for (int i = 0; i < nodeList.size(); i++) {
            GuiRemoteNode n = nodeList.get(i);
            n.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        for (GuiRemoteNode n : nodeList) {
            n.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        for (GuiRemoteNode n : nodeList) {
            n.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
    }


    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        for (GuiRemoteNode n : nodeList) {
            n.update();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public List<GuiRemoteNode> getNodeList() {
        return nodeList;
    }

    public ItemStack getStack() {
        return stack;
    }
}
