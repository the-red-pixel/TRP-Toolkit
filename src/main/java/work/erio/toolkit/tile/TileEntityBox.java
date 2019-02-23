package work.erio.toolkit.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import work.erio.toolkit.gui.GuiBlockBox;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class TileEntityBox extends TileEntity implements ITickable {
    private float angle = 0;
    private float oldAngle;

    public void showGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBlockBox(this));
    }

    public void generateContainer(int power, ItemStack item1, ItemStack item16, ItemStack item64, ItemStack container, int itemCount1, int itemCount16, int itemCount64) {
        world.setBlockState(pos, Block.getBlockFromItem(container.getItem()).getDefaultState());
        NBTTagCompound nbttagcompound = world.getTileEntity(pos).writeToNBT(new NBTTagCompound());
        NBTTagCompound nbttagcompound1 = nbttagcompound.copy();
        NBTTagList itemList = new NBTTagList();
        byte currentSlot = 0;
        currentSlot = saveItemsCompound(itemList, item1, itemCount1, currentSlot);
        currentSlot = saveItemsCompound(itemList, item16, itemCount16, currentSlot);
        currentSlot = saveItemsCompound(itemList, item64, itemCount64, currentSlot);
        nbttagcompound1.setTag("Items", itemList);
        nbttagcompound.merge(nbttagcompound1);
        world.getTileEntity(pos).readFromNBT(nbttagcompound);
        world.getTileEntity(pos).markDirty();
        IBlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    //673
    private byte saveItemsCompound(NBTTagList itemList, ItemStack item, int itemCount, byte slot) {
        byte currentSlot = slot;
        NBTTagCompound itemCompound;
        while (itemCount >= 64) {
            itemCompound = new NBTTagCompound();
            itemCompound.setByte("Slot", currentSlot);
            itemCompound.setByte("Count", (byte) 64);
            itemCompound.setShort("Damage", (short) 0);
            itemCompound.setString("id", getStringId(item));
            itemList.appendTag(itemCompound);
            itemCount -= 64;
            currentSlot++;
        }
        if (itemCount != 0) {
            itemCompound = new NBTTagCompound();
            itemCompound.setByte("Slot", currentSlot);
            itemCompound.setByte("Count", (byte) itemCount);
            itemCompound.setShort("Damage", (short) 0);
            itemCompound.setString("id", getStringId(item));
            itemList.appendTag(itemCompound);
            currentSlot++;
        }
        return currentSlot;
    }

    private String getStringId(ItemStack itemStack) {
        return itemStack.getItem().getRegistryName().toString();
    }

    @Override
    public void update() {
        oldAngle = angle;
        angle += 2;
        if (angle > 360) {
            angle %= 360;
            oldAngle %= 360;
        }
    }

    public float getAngle() {
        return angle;
    }

    public float getOldAngle() {
        return oldAngle;
    }
}
