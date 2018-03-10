package work.erio.toolkit.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import work.erio.toolkit.Toolkit;
import work.erio.toolkit.tile.TileEntityBox;

import java.io.IOException;
import java.util.function.Function;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class GuiBlockBox extends GuiScreen {
    private int power;
    private TileEntityBox tileEntity;
    private GuiButton doneButton;
    private GuiButton cancelButton;

    private GuiTextField powerTextField;

    private GuiItemSelect stack1Selector;
    private GuiItemSelect stack16Selector;
    private GuiItemSelect stack64Selector;

    public GuiBlockBox(TileEntityBox tileEntity) {
        this.tileEntity = tileEntity;
        this.power = 0;
        //initItemSelectors();
    }

    private void initItemSelectors() {
        Function<String, String> getTranslationKey = s -> String.format("message.%s.%s", Toolkit.MODID, s);
        TextComponentTranslation title1 = new TextComponentTranslation(getTranslationKey.apply("stack1"));
        title1.getStyle().setColor(TextFormatting.GREEN);
        TextComponentTranslation title16 = new TextComponentTranslation(getTranslationKey.apply("stack16"));
        title16.getStyle().setColor(TextFormatting.AQUA);
        TextComponentTranslation title64 = new TextComponentTranslation(getTranslationKey.apply("stack64"));
        title64.getStyle().setColor(TextFormatting.GOLD);
        this.stack1Selector = new GuiItemSelect(this, title1, 50, 50, itemStack -> itemStack.getMaxStackSize() == 1);
        this.stack16Selector = new GuiItemSelect(this, title16, 50, 100, itemStack -> itemStack.getMaxStackSize() == 16);
        this.stack64Selector = new GuiItemSelect(this, title64, 50, 150, itemStack -> itemStack.getMaxStackSize() == 64);

    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneButton = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel")));


        this.powerTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 50, 100, 100, 20);
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
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (button.id == 0) {
                updateTileEntity();
                this.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }

    private void updateTileEntity() {

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
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
