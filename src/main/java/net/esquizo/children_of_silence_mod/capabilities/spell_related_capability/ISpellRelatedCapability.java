package net.esquizo.children_of_silence_mod.capabilities.spell_related_capability;

import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;

public interface ISpellRelatedCapability {
    // === Mana ===
    float getMana();                                    // Get mana
    float getMaxMana();                                 // Get max mana
    float getManaRegenRate();                           // Get mana regen rate
    void setMana(float mana);                           // Set mana
    void setMaxMana(float maxMana);                     // Set max mana
    void setManaRegenRate(float regenRate);             // set mana
    void manaRegenTick();

    // === Known Spells ===
    List<Integer> knownSpellIds();                      // Get a List with all know spells Ids
    int getSpellMemory();                               // Get the max amount of spells you can learn
    boolean knowsSpell(int spellId);                    // Knows the spells
    void learnSpell(int spellId, Player player);        // Learn a spell
    void forgetSpell(int spellId);                      // Forget a spell
    void setSpellMemory(int memory);                    // Set the spell memory

    // === Equipped Spells ===
    Map<Integer, Integer> equippedSpellIds();           // <Spell slot, Spell ID>
    int getEquippedSpellId(int slot);                   // Get equipped spell
    void equipSpell(int slot, int spellId);             // Equips a spell
    void unEquipSpell(int slot);                        // Un-equips a spell

    // === Selected Spell ===
    int getSelectedSpellSlot();                         // Get selected spell slot
    void setSelectedSpellSlot(int slot);                // Set selected spell slot
    int getSelectedSpellId();                           // Derived from selected slot
    int getMaxSpellSlots();                             // Get max spell slots

    // === Cooldowns ===
    Map<Integer, Integer> getSpellCooldowns();          // <Spell ID, Spell cooldown>
    int getEquippedSpellCooldown();                     // Get cooldown of equipped spell
    void setSpellCooldown(int spellId, int ticks);      // Set a spell cooldown
    void tickCooldowns();                               // Called every tick to reduce cooldowns
}