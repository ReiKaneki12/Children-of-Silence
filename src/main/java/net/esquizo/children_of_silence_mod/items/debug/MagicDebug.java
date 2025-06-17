package net.esquizo.children_of_silence_mod.items.debug;

import net.esquizo.children_of_silence_mod.magic.gui.SpellBookMenu;
import net.esquizo.children_of_silence_mod.magic.spells.spells.FireBall;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class MagicDebug extends Item {
    public MagicDebug(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            if(pPlayer.isShiftKeyDown()){
                NetworkHooks.openScreen(
                        (ServerPlayer) pPlayer,
                        new SimpleMenuProvider(
                                (windowId, playerInventory, playerEntity) ->
                                        new SpellBookMenu(windowId, playerInventory, new SimpleContainer(1)),
                                Component.literal("Spell Book")
                        ),
                        buf ->{

                        }
                );
            }
        }
        MagicUtils.learnTheSpell(pPlayer, new FireBall());
        pPlayer.displayClientMessage(Component.literal("fireball learned: " + MagicUtils.knowsTheSpell(pPlayer, new FireBall(), false)), true );
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
