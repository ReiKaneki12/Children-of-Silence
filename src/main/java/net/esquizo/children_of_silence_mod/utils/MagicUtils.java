package net.esquizo.children_of_silence_mod.utils;

import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.ISpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedProvider;
import net.esquizo.children_of_silence_mod.networking.NetworkingHandler;
import net.esquizo.children_of_silence_mod.networking.packets.SpellRelatedCapabilitySyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import javax.swing.*;
import java.util.Optional;

public class MagicUtils {
    public static Optional<ISpellRelatedCapability> getSpellData(Player player) {
        return player.getCapability(SpellRelatedProvider.SPELL_DATA).resolve();
    }

    public static float getMana(Player player){
        return getSpellData(player).map(ISpellRelatedCapability::getMana).orElse(0f);
    }

    public static void setMana(Player player, float mana){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data -> {
            data.setMana(mana);
        });
    }

    public static float getMaxMana(Player player){
        return getSpellData(player).map(ISpellRelatedCapability::getMaxMana).orElse(20f);
    }

    public static void setMaxMana(Player player, float maxMana){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data -> {
            data.setMaxMana(maxMana);
        });
    }

    public static float getManaRegenRate(Player player){
        return getSpellData(player).map(ISpellRelatedCapability::getManaRegenRate).orElse(5f);
    }

    public static void setManaRegenRate(Player player, float manaRegenRate){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data -> {
            data.setManaRegenRate(manaRegenRate);
        });
    }

    public static void manaTick(Player player){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data -> {
            data.tickCooldowns();
            data.manaRegenTick();
            syncCapability((ServerPlayer) player);
        });
    }

    public static boolean hasEnoughMana(Player player, float mana){
        return getMana(player) >= mana;
    }

    public static void useMana(Player player, float mana){
        setMana(player, getMana(player) - mana);
    }

    public static void syncCapability(ServerPlayer player){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data -> {
            if(data instanceof SpellRelatedCapability realData){
                CompoundTag tag = realData.serializeNBT();
                NetworkingHandler.sendToPlayer(new SpellRelatedCapabilitySyncS2CPacket(tag), player);
            }
        });
    }
}
