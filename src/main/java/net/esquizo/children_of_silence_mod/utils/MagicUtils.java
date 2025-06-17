package net.esquizo.children_of_silence_mod.utils;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.ISpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedProvider;
import net.esquizo.children_of_silence_mod.magic.spells.spells.FireBall;
import net.esquizo.children_of_silence_mod.networking.NetworkingHandler;
import net.esquizo.children_of_silence_mod.networking.packets.SpellRelatedCapabilitySyncS2CPacket;
import net.esquizo.children_of_silence_mod.magic.spells.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class MagicUtils {
    public static Map<Integer, Spell> SPELL_FROM_ID = new HashMap<>();

    static{
        SPELL_FROM_ID.put(0, new Spell());
        SPELL_FROM_ID.put(1, new FireBall());
    }

    public static Optional<ISpellRelatedCapability> getSpellData(Player player) {
        return player.getCapability(SpellRelatedProvider.SPELL_DATA).resolve();
    }

    public static Spell getSpellFromId(int id){
        if (SPELL_FROM_ID.containsKey(id)) {
            return SPELL_FROM_ID.get(id);
        }
        ChildrenOfSilence.LOGGER.error("No spell found with the id: " + id);
        return new Spell();
    }

    public static List<Integer> removeSpellDuplicates(List<Integer> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
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

    public static boolean knowsTheSpell(Player player, Spell spell, boolean message){
        boolean knows = getSpellData(player).map(ISpellRelatedCapability::knownSpellIds).orElse(new ArrayList<>()).contains(spell.getId());
        if(!knows && message) player.displayClientMessage(Component.literal("you dont know the spell"), false);
        return knows;
    }

    public static void learnTheSpell(Player player, Spell spell){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data ->{
            data.learnSpell(spell.getId(), player);
        });
    }

    public static int getCooldown(Player player, Spell spell){
        return getSpellData(player).map(ISpellRelatedCapability::getSpellCooldowns).orElse(new HashMap<>()).getOrDefault(spell.getId(), 0);
    }

    public static void setCooldown(Player player, Spell spell){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data ->{
            data.setSpellCooldown(spell.getId(), spell.getCooldown());
        });
    }

    public static boolean isNotOnCooldown(Player player, Spell spell){
        int cooldown = getCooldown(player, spell);
        boolean isNot = cooldown <= 0;
        if(!isNot) player.displayClientMessage(Component.literal("you are in cooldown for " + cooldown + " ticks."), true);
        return isNot;
    }

    public static boolean hasEnoughMana(Player player, Spell spell){
        boolean hasEnough = getMana(player) >= spell.getManaCost();
        if(!hasEnough) player.displayClientMessage(Component.literal("you dont have enough mana"), false);
        return hasEnough;
    }

    public static int getSelectedSpellSlot(Player player){
        return getSpellData(player).map(ISpellRelatedCapability::getSelectedSpellSlot).orElse(1);
    }

    public static int getSelectedSpellId(Player player){
        return getSpellData(player).map(ISpellRelatedCapability::equippedSpellIds).orElse(new HashMap<>()).getOrDefault(getSelectedSpellSlot(player), 0);
    }

    public static Spell getSelectedSpell(Player player){
        return getSpellFromId(getSelectedSpellId(player));
    }

    public static void setSelectedSpell(Player player, Spell spell){
        player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(data ->{
            if(spell.getId() != 0){
                data.equippedSpellIds().put(1, spell.getId());
            }
        });
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
