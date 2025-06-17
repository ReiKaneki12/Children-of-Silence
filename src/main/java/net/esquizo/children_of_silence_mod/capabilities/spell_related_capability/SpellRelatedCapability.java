package net.esquizo.children_of_silence_mod.capabilities.spell_related_capability;

import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public class SpellRelatedCapability implements ISpellRelatedCapability, INBTSerializable<CompoundTag> {
    private float mana = 20;
    private float maxMana = 20;
    private float manaRegenRate = 5;
    private List<Integer> knownSpellIds = new ArrayList<>();
    private int spellMemory = 5;
    private Map<Integer, Integer> equippedSpellIds = new HashMap<>();
    private int selectedSpellSlot = 1;
    private int maxSpellSlots = 5;
    private Map<Integer, Integer> spellCooldowns = new HashMap<>();

    @Override
    public float getMana() {
        return this.mana;
    }

    @Override
    public float getMaxMana() {
        return this.maxMana;
    }

    @Override
    public float getManaRegenRate() {
        return this.manaRegenRate;
    }

    @Override
    public void setMana(float mana) {
        this.mana = Math.max(0, Math.min(mana, this.maxMana));
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public void setManaRegenRate(float regenRate) {
        this.manaRegenRate = regenRate;
    }

    @Override
    public List<Integer> knownSpellIds() {
        fixSpellMemoryList();
        return this.knownSpellIds;
    }

    @Override
    public int getSpellMemory() {
        return this.spellMemory;
    }

    @Override
    public boolean knowsSpell(int spellId) {
        return this.knownSpellIds.contains(spellId);
    }

    @Override
    public void learnSpell(int spellId, Player player) {
        if(!knownSpellIds().contains(spellId)) {
            if(knownSpellIds().size() < getSpellMemory()){
                this.knownSpellIds.add(spellId);
                return;
            }
            player.displayClientMessage(Component.literal("No memory left for learning this spell!").withStyle(ChatFormatting.DARK_RED), true);
        }
    }

    @Override
    public void forgetSpell(int spellId) {
        if(knownSpellIds().contains(spellId)){
            knownSpellIds.remove(spellId);
        }
    }

    @Override
    public void setSpellMemory(int memory) {
        this.spellMemory = memory;
    }

    @Override
    public Map<Integer, Integer> equippedSpellIds() {
        return this.equippedSpellIds;
    }

    @Override
    public int getEquippedSpellId(int slot) {
        if (slot < 1 || slot > maxSpellSlots) return 0;
        return equippedSpellIds.getOrDefault(slot, 0);
    }

    @Override
    public void equipSpell(int slot, int spellId) {
        equippedSpellIds().put(slot, spellId);
    }

    @Override
    public void unEquipSpell(int slot) {
        equippedSpellIds().put(slot, 0);
    }

    @Override
    public int getSelectedSpellSlot() {
        return this.selectedSpellSlot;
    }

    @Override
    public void setSelectedSpellSlot(int slot) {
        this.selectedSpellSlot = Math.max(1, Math.min(slot, maxSpellSlots));
    }

    @Override
    public int getSelectedSpellId() {
        return equippedSpellIds.getOrDefault(selectedSpellSlot, 0);
    }

    @Override
    public int getMaxSpellSlots() {
        return this.maxSpellSlots;
    }

    @Override
    public Map<Integer, Integer> getSpellCooldowns() {
        return this.spellCooldowns;
    }

    @Override
    public int getEquippedSpellCooldown() {
        return getSpellCooldowns().getOrDefault(getSelectedSpellId(), 0);
    }

    @Override
    public void setSpellCooldown(int spellId, int ticks) {
        getSpellCooldowns().put(spellId, ticks);
    }

    @Override
    public void manaRegenTick() {
        setMana(getMana() + getMaxMana() * getManaRegenRate() * 0.00025f);
    }

    @Override
    public void tickCooldowns() {
        for(Map.Entry<Integer, Integer> entry : getSpellCooldowns().entrySet()){
            if(entry.getValue() > 0) entry.setValue(entry.getValue() - 1);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("mana", this.mana);
        tag.putFloat("maxMana", this.maxMana);
        tag.putFloat("manaRegenRate", this.manaRegenRate);

        tag.putIntArray("knowSpellsId", this.knownSpellIds.stream().mapToInt(Integer::intValue).toArray());

        tag.putInt("spellMemory", this.spellMemory);

        ListTag equippedSpellIds = new ListTag();
        for(Map.Entry<Integer, Integer> entry : this.equippedSpellIds.entrySet()){
            CompoundTag entryTag = new CompoundTag();
            entryTag.putInt("key", entry.getKey());
            entryTag.putInt("value", entry.getValue());
            equippedSpellIds.add(entryTag);
        }
        tag.put("equippedSpellsId", equippedSpellIds);

        tag.putInt("selectedSpellSlot", this.selectedSpellSlot);
        tag.putInt("maxSpellSlot", this.maxSpellSlots);

        ListTag spellCooldowns = new ListTag();
        for(Map.Entry<Integer, Integer> entry : this.spellCooldowns.entrySet()){
            CompoundTag entryTag = new CompoundTag();
            entryTag.putInt("key", entry.getKey());
            entryTag.putInt("value", entry.getValue());
            spellCooldowns.add(entryTag);
        }
        tag.put("spellCooldowns", spellCooldowns);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.mana = tag.getFloat("mana");
        this.maxMana = tag.getFloat("maxMana");
        this.manaRegenRate = tag.getFloat("manaRegenRate");

        this.knownSpellIds = new ArrayList<>(Arrays.stream(tag.getIntArray("knowSpellsId")).boxed().toList());

        this.spellMemory = tag.getInt("spellMemory");

        if (tag.contains("equippedSpellsId", Tag.TAG_LIST)) {
            ListTag listTag = tag.getList("equippedSpellsId", Tag.TAG_COMPOUND);
            for (Tag listEntry : listTag) {
                CompoundTag entryTag = (CompoundTag) listEntry;
                int key = entryTag.getInt("key");
                int value = entryTag.getInt("value");
                this.equippedSpellIds.put(key, value);
            }
        }

        this.selectedSpellSlot = tag.getInt("selectedSpellSlot");
        this.maxSpellSlots = tag.getInt("maxSpellSlot");

        if (tag.contains("spellCooldowns", Tag.TAG_LIST)) {
            ListTag listTag = tag.getList("spellCooldowns", Tag.TAG_COMPOUND);
            for (Tag listEntry : listTag) {
                CompoundTag entryTag = (CompoundTag) listEntry;
                int key = entryTag.getInt("key");
                int value = entryTag.getInt("value");
                this.spellCooldowns.put(key, value);
            }
        }
    }

    public void copyFrom(SpellRelatedCapability other){
        this.mana = other.mana;
        this.maxMana = other.maxMana;
        this.manaRegenRate = other.manaRegenRate;
        this.knownSpellIds = new ArrayList<>(other.knownSpellIds);
        this.spellMemory = other.spellMemory;
        this.equippedSpellIds = new HashMap<>(other.equippedSpellIds);
        this.selectedSpellSlot = other.selectedSpellSlot;
        this.maxSpellSlots = other.maxSpellSlots;
        this.spellCooldowns = new HashMap<>(other.spellCooldowns);
    }

    private void fixSpellMemoryList(){
        this.knownSpellIds = MagicUtils.removeSpellDuplicates(this.knownSpellIds);
    }
}
