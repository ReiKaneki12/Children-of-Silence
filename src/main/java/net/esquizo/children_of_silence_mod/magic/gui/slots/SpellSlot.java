package net.esquizo.children_of_silence_mod.magic.gui.slots;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.magic.spells.Spell;
import net.esquizo.children_of_silence_mod.magic.spells.SpellItem;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class SpellSlot extends Slot {
    private final Player player;

    public SpellSlot(Container pContainer, int pSlot, int pX, int pY, Player player) {
        super(pContainer, pSlot, pX, pY);
        this.player = player;
    }

    @Override
    public boolean mayPickup(Player pPlayer) {
        return true;
    }

    @Override
    public boolean mayPlace(ItemStack pStack) {
        return false;
    }

    @Override
    public ItemStack getItem() {
        if(this.getSlotIndex() == 6 && MagicUtils.knowsTheSpell(this.player, MagicUtils.getSpellFromId(this.getSlotIndex()-5), false)){
            return MagicUtils.getSpellFromId(this.getSlotIndex()-5).asItem();
        }
        if(this.getSlotIndex() == 0){
            if(MagicUtils.getSelectedSpell(this.player).getId() != 0){
                return MagicUtils.getSelectedSpell(this.player).asItem();
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public Optional<ItemStack> tryRemove(int pCount, int pDecrement, Player pPlayer){
        if(!pPlayer.level().isClientSide){
            if(this.getItem().getItem() instanceof SpellItem spell){
                MagicUtils.setSelectedSpell(pPlayer, spell.asSpell(this.getItem()));
            }
        }

        return Optional.empty();
    }

}
