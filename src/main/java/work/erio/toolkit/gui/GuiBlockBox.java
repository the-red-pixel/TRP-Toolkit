package work.erio.toolkit.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import scala.Char;
import work.erio.toolkit.tile.TileEntityBox;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Erioifpud on 2018/3/8.
 */
public class GuiBlockBox extends GuiScreen {
    private int power;
    private TileEntityBox tileEntity;
    private GuiButton doneButton;
    private GuiButton cancelButton;

    private GuiTextField powerTextField;

    public GuiBlockBox(TileEntityBox tileEntity) {
        this.tileEntity = tileEntity;
        this.power = 0;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.powerTextField.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneButton = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done")));
        this.cancelButton = this.addButton(new GuiButton(1, this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel")));


        this.powerTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 50, 100, 100, 20);
        this.powerTextField.setMaxStringLength(10);
        this.powerTextField.setFocused(true);
        this.powerTextField.setText("0");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (button.id == 0) {
                this.mc.displayGuiScreen((GuiScreen) null);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        //TODO checkKeyInput(typedChar, keyCode);
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
