package net.esquizo.children_of_silence_mod.items.hayate;

import net.esquizo.children_of_silence_mod.Init.ItemInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ElvenKatana extends SwordItem {
    public ElvenKatana(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.isShiftKeyDown()) pPlayer.setItemInHand(pUsedHand, ItemInit.OVERSIZED.get().getDefaultInstance());
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}