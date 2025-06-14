package net.esquizo.children_of_silence_mod.Init;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.ISpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChildrenOfSilence.MOD_ID)
public class CapabilityInit {
    private static final String MOD_ID = ChildrenOfSilence.MOD_ID;

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.register(ISpellRelatedCapability.class);
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent event){
        if (!(event.getObject() instanceof LivingEntity entity)) {
            return;
        }

        if (!entity.getCapability(SpellRelatedProvider.SPELL_DATA).isPresent()){
            event.addCapability(
                    new ResourceLocation(MOD_ID, "spell_data"),
                    new SpellRelatedProvider()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
        Player original = event.getOriginal();
        Player clone = event.getEntity();

        original.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(oldData -> {
            clone.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(newData -> {
                ((SpellRelatedCapability) newData).copyFrom((SpellRelatedCapability) oldData);
            });
        });
    }
}
