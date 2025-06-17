package net.esquizo.children_of_silence_mod.Init;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.magic.gui.SpellBookMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ChildrenOfSilence.MOD_ID);

    public static final RegistryObject<MenuType<SpellBookMenu>> SPELL_BOOK_MENU =
            MENUS.register("my_menu", () -> IForgeMenuType.create(SpellBookMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
