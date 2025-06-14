package net.esquizo.children_of_silence_mod.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class ManaOverlay {
    private static final ResourceLocation MANA_BAR_TEXTURE = new ResourceLocation(ChildrenOfSilence.MOD_ID, "textures/gui/mana_bar.png");

    public static void renderManaBar(GuiGraphics graphics){
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        int x = screenWidth / 2 + 10;
        int y = screenHeight - 49;

        int mana = (int) (MagicUtils.getMana(mc.player)*100f);
        int maxMana = (int) (MagicUtils.getMaxMana(mc.player)*100f);

        float fillPercent = maxMana > 0 ? (float) mana / maxMana : 0;

        renderManaBar(graphics, x, y, fillPercent, (int) MagicUtils.getMana(mc.player), (int) MagicUtils.getMaxMana(mc.player));
    }

    private static void renderManaBar(GuiGraphics graphics, int x, int y, float fillPercentage, int currentMana, int maxMana) {
        RenderSystem.setShaderTexture(0, MANA_BAR_TEXTURE);

        int barWidth = 81;
        int barHeight = 9;
        int scale = 3;
        int textureWidth = barWidth * scale;
        int textureHeight = barHeight * scale;

        //Background
        graphics.blit(MANA_BAR_TEXTURE, x, y, barWidth, barHeight,
                0, 0, textureWidth, textureHeight,
                256, 256);

        //Mana
        int fillWidth = Math.round(barWidth * fillPercentage);
        if (fillWidth > 0) {
            int fillTextureWidth = fillWidth * scale;
            graphics.blit(MANA_BAR_TEXTURE, x, y, fillWidth, barHeight,
                    0, textureHeight, fillTextureWidth, textureHeight,
                    256, 256);
        }

        //Text
        String manaText = currentMana + "/" + maxMana;
        int textWidth = Minecraft.getInstance().font.width(manaText);
        graphics.drawString(Minecraft.getInstance().font, manaText,
                x + (barWidth - textWidth) / 2, y - 10, 0x55FFFF);
    }
}
