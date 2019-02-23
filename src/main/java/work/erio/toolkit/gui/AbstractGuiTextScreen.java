package work.erio.toolkit.gui;

import io.netty.buffer.Unpooled;

import java.io.IOException;
import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import work.erio.toolkit.tile.TileEntityJS;

@SideOnly(Side.CLIENT)
public abstract class AbstractGuiTextScreen<T extends TileEntity> extends GuiScreen {

    protected GuiTextField textField;
    protected final T te;
    protected GuiButton doneBtn;
    protected GuiButton cancelBtn;

    public AbstractGuiTextScreen(T te) {
        super();
        this.te = te;
    }

    public void updateScreen() {
        this.textField.updateCursorCounter();
    }

    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.doneBtn = this.addButton(new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
        this.cancelBtn = this.addButton(new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
        this.textField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen(null);
            } else if (button.id == 0) {
                this.onConfirm();
                this.mc.displayGuiScreen(null);
            }
        }
    }

    protected abstract void onConfirm();

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.textField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 1) {
                this.actionPerformed(this.cancelBtn);
            }
        } else {
            this.actionPerformed(this.doneBtn);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected abstract String getTitle();

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.getTitle(), this.width / 2, 20, 16777215);
        this.textField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}