package net.esquizo.children_of_silence_mod.magic.spells;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SpellItem extends Item {
    public SpellItem(Properties pProperties) {
        super(pProperties);
    }

    public void setSpellId(ItemStack stack, int id) {
        stack.getOrCreateTag().putInt("spellId", id);
    }

    public int getSpellId(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("spellId")) {
            return stack.getTag().getInt("spellId");
        }
        return 0;
    }

    public Spell asSpell(ItemStack stack){
        return MagicUtils.getSpellFromId(getSpellId(stack));
    }

    @Override
    public Component getName(ItemStack stack){
        if(stack.hasTag() && stack.getTag().contains("spellId")){
            int id = getSpellId(stack);
            return Component.translatable("item." + ChildrenOfSilence.MOD_ID + ".spell_item.id_" + id);
        }
        return super.getName(stack);
    }
}
