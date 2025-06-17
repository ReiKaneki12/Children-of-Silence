package net.esquizo.children_of_silence_mod.utils;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.networking.NetworkingHandler;
import net.esquizo.children_of_silence_mod.networking.packets.OpenSpellMenuC2S;
import net.esquizo.children_of_silence_mod.networking.packets.UseSpellC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChildrenOfSilence.MOD_ID, value = Dist.CLIENT)
public class ClientKeyInputHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END || Minecraft.getInstance().player == null) return;

        // Open Spell Book Key
        if (ModKeyMappings.SPELL_MENU_KEY.consumeClick()) {
            NetworkingHandler.sendToServer(new OpenSpellMenuC2S());
        }

        // Use Spell
        if (ModKeyMappings.USE_SPELL_KEY.consumeClick()){
            NetworkingHandler.sendToServer(new UseSpellC2S());
        }
    }
}
