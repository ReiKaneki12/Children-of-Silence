package net.esquizo.children_of_silence_mod.magic.spells;

import net.esquizo.children_of_silence_mod.Init.ItemInit;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Spell {
    protected int id;
    protected float manaCost;
    protected int cooldown;

    protected void setValues(int id, float mana, int cooldown){
        this.id = id;
        this.manaCost = mana;
        this.cooldown = cooldown;
    }

    public int getId(){
        return this.id;
    }

    public float getManaCost(){
        return this.manaCost;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public ItemStack asItem(){
        ItemStack stack = new ItemStack(ItemInit.SPELL_ITEM.get());

        if (stack.getItem() instanceof SpellItem spellItem) {
            spellItem.setSpellId(stack, this.id);
        }

        return stack;
    }

    public void useSpell(Player player){

    }

    public boolean canUse(Player player){
        return MagicUtils.knowsTheSpell(player, this, true) && MagicUtils.isNotOnCooldown(player, this) && MagicUtils.hasEnoughMana(player, this);
    }
}
