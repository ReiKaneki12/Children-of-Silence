package net.esquizo.children_of_silence_mod.items.draken;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class StingOfSacrament extends SwordItem{
    public StingOfSacrament(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public static float stingDamage(float damage, LivingEntity enemy, Entity attacker){
        boolean isDarkness = attacker.getPersistentData().getBoolean("stingIsDarkness");
        return isDarkness ? damage * 2 : damage;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        CompoundTag tag = pPlayer.getPersistentData();
        if (!pLevel.isClientSide) tag.putBoolean("stingIsDarkness", !tag.getBoolean("stingIsDarkness"));
        boolean isDarkness = tag.getBoolean("stingIsDarkness");
        pPlayer.displayClientMessage(Component.literal(isDarkness+""), true);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}