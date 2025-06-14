package net.esquizo.children_of_silence_mod.events;

import net.esquizo.children_of_silence_mod.overlays.ManaOverlay;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = {net.minecraftforge.api.distmarker.Dist.CLIENT})
public class ClientEvents {
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event){
        if(event.getOverlay().id().toString().equals("minecraft:hotbar")){
            ManaOverlay.renderManaBar(event.getGuiGraphics());
        }
    }

}
