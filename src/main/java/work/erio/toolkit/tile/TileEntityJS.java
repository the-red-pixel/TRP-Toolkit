package work.erio.toolkit.tile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import work.erio.toolkit.gui.GuiBlockBox;
import work.erio.toolkit.gui.GuiBlockJS;
import work.erio.toolkit.util.JSEngine;

import java.util.ArrayList;
import java.util.List;

public class TileEntityJS extends TileEntity implements ITickable {

//    private NonNullList<ItemStack> codeItemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private String customName;
    private String code;

    public TileEntityJS() {
        this.code = "";
    }

    public void showGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBlockJS(this));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
//        processor.readFromNBT(compound.getCompoundTag(NBT_PROCESSOR));

//        codeItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
//        ItemStackHelper.loadAllItems(compound, codeItemStacks);

//        loadTime = compound.getShort(NBT_LOAD_TIME);
        if (compound.hasKey("Code", 8)) {
            this.code = compound.getString("Code");
        }

//        if (compound.hasKey("CustomName", 8)) {
//            this.customName = compound.getString("CustomName");
//        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound c = super.writeToNBT(compound);
//        c.setTag(NBT_PROCESSOR, processor.writeToNBT());

//        c.setShort(NBT_LOAD_TIME, (short) loadTime);
//        ItemStackHelper.saveAllItems(c, codeItemStacks);

        if (!this.code.isEmpty()) {
            c.setString("Code", this.code);
        }

//        if (this.hasCustomName()) {
//            c.setString("CustomName", this.customName);
//        }
        return c;
    }

//    @Override
//    public int getSizeInventory() {
//        return codeItemStacks.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        for (ItemStack itemstack : codeItemStacks) {
//            if (!itemstack.isEmpty()) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public ItemStack getStackInSlot(int index) {
//        return index >= 0 && index < codeItemStacks.size() ? codeItemStacks.get(index) : ItemStack.EMPTY;
//    }
//
//    @Override
//    public ItemStack decrStackSize(int index, int count) {
//        markDirty();
//        return ItemStackHelper.getAndSplit(codeItemStacks, index, count);
//    }
//
//    @Override
//    public ItemStack removeStackFromSlot(int index) {
//        markDirty();
//        return ItemStackHelper.getAndRemove(codeItemStacks, index);
//    }

    public void updateCode(String code) {
        this.code = code;
        markDirty();
    }

    public String getCode() {
        markDirty();
        return this.code;
    }

    public void evalCode() {
        if (!this.code.isEmpty()) {
            new JSEngine().eval(this.code);
        }
    }

//    @Override
//    public void setInventorySlotContents(int index, ItemStack stack) {
//        if (index != 0) {
//            return;
//        }
//
//        codeItemStacks.set(index, stack);
//
//        if (!stack.isEmpty()) {
//            loadCode(stack);
//        } else {
//            unloadCode();
//        }
//
//        markDirty();
//    }

    private static void addLines(List<String> lines, String toAdd) {
        for (String s : toAdd.split("\\n\\r?")) {
            lines.add(s);
        }
    }

    public static boolean isBook(ItemStack stack) {
        return (stack.getItem() instanceof ItemWritableBook || stack.getItem() instanceof ItemWrittenBook) && stack.hasTagCompound();
    }

    private String getCodeFromBook(ItemStack stack) {
        if (!isBook(stack)) {
            return "";
        }
        NBTTagList pages = stack.getTagCompound().getTagList("pages", 8);
        if (pages == null) {
            return "";
        }
        boolean signed = stack.getTagCompound().hasKey("author");
        List<String> code = new ArrayList<>(pages.tagCount());
        JsonParser parser = null;
        for (int i = 0; i < pages.tagCount(); ++i) {
            if (signed) {
                if (parser == null) {
                    parser = new JsonParser();
                }
                JsonObject o = parser.parse(pages.getStringTagAt(i)).getAsJsonObject();
                addLines(code, o.get("text").getAsString());
            } else {
                addLines(code, pages.getStringTagAt(i));
            }
        }
        return code.stream().reduce("", String::concat);
    }

    private void loadCode(ItemStack stack) {
        if (world.isRemote) {
            return;
        }

        this.code = getCodeFromBook(stack);
    }

    private void unloadCode() {
        this.code = "";
    }

//    @Override
//    public int getInventoryStackLimit() {
//        return 1;
//    }
//
//    @Override
//    public boolean isUsableByPlayer(EntityPlayer player) {
//        return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
//    }
//
//    @Override
//    public void openInventory(EntityPlayer player) {
//
//    }
//
//    @Override
//    public void closeInventory(EntityPlayer player) {
//
//    }
//
//    @Override
//    public boolean isItemValidForSlot(int index, ItemStack stack) {
//        return isBook(stack);
//    }
//
//    @Override
//    public int getField(int id) {
//        return 0;
//    }
//
//    @Override
//    public void setField(int id, int value) {
//
//    }
//
//    @Override
//    public int getFieldCount() {
//        return 1;
//    }
//
//    @Override
//    public void clear() {
//
//    }

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }
    }

//    @Override
//    public String getName() {
//        return hasCustomName() ? this.customName : "container.js_block";
//    }
//
//    public void setName(String name) {
//        this.customName = name;
//    }
//
//    @Override
//    public boolean hasCustomName() {
//        return customName != null && !customName.isEmpty();
//    }
}
