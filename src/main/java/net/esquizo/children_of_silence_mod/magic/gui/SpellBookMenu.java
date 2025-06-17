package net.esquizo.children_of_silence_mod.magic.gui;

import net.esquizo.children_of_silence_mod.Init.MenuInit;
import net.esquizo.children_of_silence_mod.magic.gui.slots.SpellSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class SpellBookMenu extends AbstractContainerMenu {
    private final Container container;
    private final Player player;

    public SpellBookMenu(int id, Inventory playerInventory, FriendlyByteBuf buf) {
        this(id, playerInventory, new SimpleContainer(36));
    }


    public Player getPlayer(){
        return this.player;
    }

    public SpellBookMenu(int id, Inventory playerInv, Container container) {
        super(MenuInit.SPELL_BOOK_MENU.get(), id);
        this.container = container;
        Player player = playerInv.player;
        this.player = player;

        this.addSlot(new SpellSlot(container, 0, 70, 61, player));
        this.addSlot(new SpellSlot(container, 1, 30, 54, player));
        this.addSlot(new SpellSlot(container, 2, 70, 21, player));
        this.addSlot(new SpellSlot(container, 3, 110, 54, player));
        this.addSlot(new SpellSlot(container, 4, 96, 101, player));
        this.addSlot(new SpellSlot(container, 5, 44, 101, player));

        // First row
        for(int row = 0; row < 5; row++){
            for(int colum = 0; colum < 3; colum++){
                int slotId = 6 + colum + row * 6;
                int pX = 156 + 19 * colum;
                int pY = 18 + 19 * row;
                this.addSlot(new SpellSlot(container, slotId, pX, pY, player));
            }
        }

        // Second row
        for(int row = 0; row < 5; row++){
            for(int colum = 0; colum < 3; colum++){
                int slotId = 9 + colum + row * 6;
                int pX = 214 + 19 * colum;
                int pY = 18 + 19 * row;
                this.addSlot(new SpellSlot(container, slotId, pX, pY, player));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
