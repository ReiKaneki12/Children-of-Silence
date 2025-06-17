package net.esquizo.children_of_silence_mod.magic.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class SpellBookScreen extends AbstractContainerScreen<SpellBookMenu> {
    private static final ResourceLocation TEXTURE_LEFT = new ResourceLocation(ChildrenOfSilence.MOD_ID, "textures/gui/spell_book_left.png");
    private static final ResourceLocation TEXTURE_RIGHT = new ResourceLocation(ChildrenOfSilence.MOD_ID, "textures/gui/spell_book_right.png");

    public SpellBookScreen(SpellBookMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 290;
        this.imageHeight = 188;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, TEXTURE_LEFT);
        guiGraphics.blit(TEXTURE_LEFT, leftPos, topPos, 0, 0, 145, imageHeight, 145, imageHeight);

        RenderSystem.setShaderTexture(0, TEXTURE_RIGHT);
        guiGraphics.blit(TEXTURE_RIGHT, leftPos + 145, topPos, 0, 0, 145, imageHeight, 145, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        Player player = ((SpellBookMenu) this.menu).getPlayer();
        graphics.drawString(this.font, Component.literal("" + (int) MagicUtils.getManaRegenRate(player)), 264, 137, 0xDC9613, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
