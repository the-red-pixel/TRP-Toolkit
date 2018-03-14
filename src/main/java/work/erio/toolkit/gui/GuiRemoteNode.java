package work.erio.toolkit.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import work.erio.toolkit.tile.TileEntityRemoteSwitch;

import java.util.List;

/**
 * Created by Erioifpud on 2018/3/13.
 */
public class GuiRemoteNode extends Gui {
    private static Minecraft mc = Minecraft.getMinecraft();
    private TileEntityRemoteSwitch te;
    private GuiRemoteController parent;
    private BlockPos pos;
    private double x;
    private double y;
    private double offsetX;
    private double offsetY;
    private boolean isDragging = false;
    //private boolean isOn;
    private int width = 90;
    private int height = 55;
    private int btnHeight = 10;

    public GuiRemoteNode(GuiRemoteController parent, BlockPos pos) {
        this.parent = parent;
        this.pos = pos;
        this.x = 0;
        this.y = 0;
        this.offsetX = 0;
        this.offsetY = 0;
        this.te = (TileEntityRemoteSwitch) mc.world.getTileEntity(pos);

    }



    private void flipState() {
        //TileEntity tileEntity = mc.world.getTileEntity(pos);
        //if (tileEntity instanceof TileEntityRemoteSwitch) {
            //TileEntityRemoteSwitch te = (TileEntityRemoteSwitch) tileEntity;
            System.out.println(te.isPowered());
            te.setPowered(!te.isPowered());
            //isOn = te.isPowered();
            System.out.println(te.isPowered());
        //}
    }

    private void test(ItemStack stack) {
        NBTTagCompound root = stack.getTagCompound();
        if (root != null && root.hasKey("posList")) {
            NBTTagList posListTag = root.getTagList("posList", Constants.NBT.TAG_COMPOUND);
            if (!posListTag.hasNoTags()) {
                NBTTagCompound posCompound = posListTag.getCompoundTagAt(0);
                BlockPos pos = new BlockPos(posCompound.getInteger("x"), posCompound.getInteger("y"), posCompound.getInteger("z"));
                TileEntity tileEntity = mc.world.getTileEntity(pos);
                if (tileEntity instanceof TileEntityRemoteSwitch) {
                    TileEntityRemoteSwitch te = (TileEntityRemoteSwitch) tileEntity;

                    te.setPowered(!te.isPowered());
                    System.out.println(te.isPowered());
                }
            }
        }
    }

    public void draw(int mouseX, int mouseY, float partialTicks) {

        FontRenderer fontRenderer = mc.fontRenderer;
        if (isDragging) {
            x = mouseX - offsetX;
            y = mouseY - offsetY;
        }
        if (isOnTop()) {
            drawRect((int) x - 1, (int) y - 1, (int) x + width + 1, (int) y + height + 1, 0xff66c5cc);
        }
        drawRect((int) x, (int) y, (int) x + width, (int) y + height, 0xff222735);
        drawRect((int) x, (int) y + height - btnHeight, (int) x + width, (int) y + height, te.isPowered() ? 0xff27A7B1 : 0xff1C202B);

        drawString(fontRenderer, "Remote Switch", (int) x + 5, (int) y + 3, 0xffffffff);
        drawString(fontRenderer, String.format("(%d, %d, %d)", pos.getX(), pos.getY(), pos.getZ()), (int) x + 5, (int) y + 13, 0xffffffff);

    }

    public void update() {
        //this.prevX = this.x;
        //this.prevY = this.y;
        if (isDragging) {

        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            isDragging = false;
            offsetX = 0;
            offsetY = 0;
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
        if (isOnTop()) {
            if (isMouseOverAndLeftClick(mouseX, mouseY, mouseButton)) {
                isDragging = true;
                //x = mouseX - offsetX;
                //y = mouseY - offsetY;
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!isOnTop()) {
            if (isMouseOverAndLeftClick(mouseX, mouseY, mouseButton)) {
                if (isOverOther(mouseX, mouseY)) {
                    parent.getNodeList().remove(this);
                    parent.getNodeList().add(this);
                }
            }
        } else {
            this.offsetX = (double) mouseX - x;
            this.offsetY = (double) mouseY - y;
            if (isMouseOverBtnAndLeftClick(mouseX, mouseY, mouseButton)) {
                flipState();
                mc.world.markAndNotifyBlock(pos, mc.world.getChunkFromBlockCoords(pos), mc.world.getBlockState(pos), mc.world.getBlockState(pos), 4);
                //test(parent.getStack());
            }
        }
    }

    private boolean isMouseOverAllAndLeftClick(int mouseX, int mouseY, int button) {
        return isMouseOverAll(mouseX, mouseY) && button == 0;
    }


    private boolean isMouseOverAll(int mouseX, int mouseY) {
        return isMouseOver(mouseX, mouseY) || isMouseOverBtn(mouseX, mouseY);
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height - btnHeight;
    }

    private boolean isMouseOverBtn(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y + height - btnHeight && mouseY <= y + height;
    }

    private boolean isMouseOverBtnAndLeftClick(int mouseX, int mouseY, int button) {
        return isMouseOverBtn(mouseX, mouseY) && button == 0;
    }

    private boolean isMouseOverAndLeftClick(int mouseX, int mouseY, int button) {
        return isMouseOver(mouseX, mouseY) && button == 0;
    }

    public boolean isOnTop() {
        List<GuiRemoteNode> list = parent.getNodeList();
        return list.indexOf(this) == list.size() - 1;
    }

    /*
    public boolean isOnSecondTop() {
        List<GuiRemoteNode> list = parent.getNodeList();
        return list.indexOf(this) == list.size() - 2;
    }
    */

    public boolean isOverOther(int mouseX, int mouseY) {
        List<GuiRemoteNode> list = parent.getNodeList();
        if (isMouseOver(mouseX, mouseY)) {
            for (GuiRemoteNode n : list) {
                if (n.isMouseOver(mouseX, mouseY) && list.indexOf(n) > list.indexOf(this)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
