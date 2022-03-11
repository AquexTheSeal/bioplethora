package io.github.bioplethora.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.gui.container.AbstractReinforcingContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ReinforcingTableScreen extends ContainerScreen<AbstractReinforcingContainer> implements IContainerListener {
    private final ResourceLocation GUI = new ResourceLocation(Bioplethora.MOD_ID, "textures/gui/container/reinforcing_table.png");

    public ReinforcingTableScreen(AbstractReinforcingContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    protected void subInit() {
    }

    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.getXSize(), this.getYSize());

        this.blit(matrixStack, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);

        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem()) && !this.menu.getSlot(3).hasItem()) {
            this.blit(matrixStack, i + 99, j + 45, this.imageWidth, 0, 28, 21);
        }
    }

    @Override
    public void refreshContainer(Container pContainerToSend, NonNullList<ItemStack> pItemsList) {
        this.slotChanged(pContainerToSend, 0, pContainerToSend.getSlot(0).getItem());
    }

    @Override
    public void slotChanged(Container pContainerToSend, int pSlotInd, ItemStack pStack) {

    }

    @Override
    public void setContainerData(Container pContainer, int pVarToUpdate, int pNewValue) {

    }
}
