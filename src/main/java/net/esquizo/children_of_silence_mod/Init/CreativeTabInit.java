package net.esquizo.children_of_silence_mod.Init;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChildrenOfSilence.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DRAKEN_TAB = CREATIVE_MODE_TABS.register("draken_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemInit.DRAKEN_TAB.get()))
                    .title(Component.translatable("creativetab.draken_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ItemInit.STING_OF_SACRAMENT.get());
                    })
                    .build());
    public static final RegistryObject<CreativeModeTab> HAYATE_TAB = CREATIVE_MODE_TABS.register("hayate_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemInit.HAYATE_TAB.get()))
                    .title(Component.translatable("creativetab.hayate_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ItemInit.ELVEN_KATANA.get());
                        output.accept(ItemInit.OVERSIZED.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
