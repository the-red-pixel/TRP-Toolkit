package work.erio.toolkit.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import work.erio.toolkit.common.EnumSupportedContainer;
import work.erio.toolkit.tile.TileEntityBox;
import work.erio.toolkit.util.TextUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class GuiBlockBox extends GuiScreen {
    private static final int MAX_POWER = 896;
    private static final int MIN_POWER = 0;
    private int power;
    private TileEntityBox tileEntity;
    private GuiButton doneButton;
    private GuiButton cancelButton;

    private GuiTextField powerTextField;

    private static GuiItemSelect stack1Selector;
    private static GuiItemSelect stack16Selector;
    private static GuiItemSelect stack64Selector;

    private static GuiItemSelect containerSelector;

    public GuiBlockBox(TileEntityBox tileEntity) {
        this.tileEntity = tileEntity;
        this.power = 0;
        //initItemSelectors();
    }

    private void initItemSelectors() {
        //int width = this.width / 2 - GuiItemSelect.WIDTH / 2;
        int height = this.height / 2 - GuiItemSelect.HEIGHT / 2;
        TextComponentTranslation title1 = new TextComponentTranslation(TextUtils.getTranslationKey("stack1"));
        title1.getStyle().setColor(TextFormatting.GREEN);
        TextComponentTranslation title16 = new TextComponentTranslation(TextUtils.getTranslationKey("stack16"));
        title16.getStyle().setColor(TextFormatting.AQUA);
        TextComponentTranslation title64 = new TextComponentTranslation(TextUtils.getTranslationKey("stack64"));
        title64.getStyle().setColor(TextFormatting.GOLD);
        TextComponentTranslation titleContainer = new TextComponentTranslation(TextUtils.getTranslationKey("container"));
        titleContainer.getStyle().setColor(TextFormatting.YELLOW);
        this.stack1Selector = new GuiItemSelect(this, title1, (int) (width * 0.3) - GuiItemSelect.WIDTH / 2, height, itemStack -> itemStack.getMaxStackSize() == 1);
        this.stack16Selector = new GuiItemSelect(this, title16, width / 2 - GuiItemSelect.WIDTH / 2, height, itemStack -> itemStack.getMaxStackSize() == 16);
        this.stack64Selector = new GuiItemSelect(this, title64, (int) (width * 0.7) - GuiItemSelect.WIDTH / 2, height, itemStack -> itemStack.getMaxStackSize() == 64);

        EnumSupportedContainer[] infos = EnumSupportedContainer.values();
        //TODO need optimize
        this.containerSelector = new GuiItemSelect(this, titleContainer, width / 2 - GuiItemSelect.WIDTH / 2, height + GuiItemSelect.HEIGHT * 2, itemStack -> Arrays.stream(infos).anyMatch(info -> info.getItemStack().getUnlocalizedName().equals(itemStack.getUnlocalizedName())));

    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneButton = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel")));


        this.powerTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 50, this.height / 2 - 40, 100, 20);
        this.powerTextField.setMaxStringLength(10);
        this.powerTextField.setFocused(true);
        this.powerTextField.setText("0");

        initItemSelectors();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.powerTextField.updateCursorCounter();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.stack1Selector.onWheelChanged();
        this.stack16Selector.onWheelChanged();
        this.stack64Selector.onWheelChanged();
        this.containerSelector.onWheelChanged();
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (button.id == 0) {
                sendContainerInfo();
                this.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }

    private void sendContainerInfo() {
        ItemStack item1 = stack1Selector.getItemStack();
        ItemStack item16 = stack16Selector.getItemStack();
        ItemStack item64 = stack64Selector.getItemStack();
        ItemStack container = containerSelector.getItemStack();
        int stack1ItemCount = 0;
        int stack16ItemCount = 0;
        int stack64ItemCount = 0;
        try {
            power = Integer.parseInt(powerTextField.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (power > MAX_POWER) {
            power = MAX_POWER;
        } else if (power < MIN_POWER) {
            power = MIN_POWER;
        }
        String containerName = containerSelector.getItemStack().getUnlocalizedName();
        int slot = EnumSupportedContainer.findByUnlocalizedName(containerName).map(EnumSupportedContainer::getSlot).get();

        int itemCount = calcItems(power, slot);
        int tmpCount = itemCount;

        if (itemCount >= 64) {
            stack1ItemCount = itemCount / 64;
            itemCount %= 64;
        }
        if (itemCount >= 4) {
            stack16ItemCount = itemCount / 4;
            itemCount %= 4;
        }
        stack64ItemCount = itemCount;
        tileEntity.generateContainer(power, item1, item16, item64, container, stack1ItemCount, stack16ItemCount, stack64ItemCount);
        showOutputMessage(power, container, item1, item16, item64);
    }

    private void showOutputMessage(int power, ItemStack container, ItemStack item1, ItemStack item16, ItemStack item64) {
        TextUtils.printTranslationFormat(mc.player, "box_output", TextFormatting.GOLD, power, container.getDisplayName(), item1.getDisplayName(), item16.getDisplayName(), item64.getDisplayName());
    }

    private int calcItems(int power, int slot) {
        return Math.max(power, (int) Math.ceil(((double) slot * 64 / 14) * (power - 1)));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        //TODO input number only
        this.powerTextField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != Keyboard.KEY_RETURN && keyCode != Keyboard.KEY_NUMPADENTER) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                this.actionPerformed(this.cancelButton);
            }
        } else {
            this.actionPerformed(this.doneButton);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.powerTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.powerTextField.drawTextBox();
        this.stack1Selector.draw(mouseX, mouseY);
        this.stack16Selector.draw(mouseX, mouseY);
        this.stack64Selector.draw(mouseX, mouseY);
        this.containerSelector.draw(mouseX, mouseY);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }

    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }
}
