package net.esquizo.children_of_silence_mod.networking.packets;

import net.esquizo.children_of_silence_mod.magic.gui.SpellBookMenu;
import net.esquizo.children_of_silence_mod.utils.MagicUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class UseSpellC2S {
    public UseSpellC2S(){

    }

    public UseSpellC2S(FriendlyByteBuf buf){

    }

    public void toByte(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                useSpell(player);
            }
        });
        context.setPacketHandled(true);
    }

    private void useSpell(ServerPlayer player){
        if(MagicUtils.getSelectedSpell(player).getId() != 0){
            MagicUtils.getSelectedSpell(player).useSpell(player);
        }
    }
}
