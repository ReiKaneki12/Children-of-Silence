package net.esquizo.children_of_silence_mod.events;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.Init.ItemInit;
import net.esquizo.children_of_silence_mod.Init.MenuInit;
import net.esquizo.children_of_silence_mod.magic.gui.SpellBookScreen;
import net.esquizo.children_of_silence_mod.utils.ModKeyMappings;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ChildrenOfSilence.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event){
        event.register(ModKeyMappings.SPELL_MENU_KEY);
        event.register(ModKeyMappings.USE_SPELL_KEY);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->{
            MenuScreens.register(MenuInit.SPELL_BOOK_MENU.get(), SpellBookScreen::new);
        });

        event.enqueueWork(() -> {
            ItemProperties.register(
                    ItemInit.SPELL_ITEM.get(),
                    new ResourceLocation("spell_id"),
                    (stack, level, entity, seed) -> {
                        if (stack.hasTag() && stack.getTag().contains("spellId")) {
                            return stack.getTag().getInt("spellId");
                        }
                        return 0;
                    }
            );
        });
    }
}
