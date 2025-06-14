package net.esquizo.children_of_silence_mod.Init;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.items.debug.MagicDebug;
import net.esquizo.children_of_silence_mod.items.draken.StingOfSacrament;
import net.esquizo.children_of_silence_mod.items.hayate.ElvenKatana;
import net.esquizo.children_of_silence_mod.items.hayate.Oversized;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChildrenOfSilence.MOD_ID);

    //Draken Items
    public static final RegistryObject<Item> STING_OF_SACRAMENT = ITEMS.register("sting_of_sacrament",
            () -> new StingOfSacrament(Tiers.DIAMOND, 5, -2f, new Item.Properties()));

    //Hayate Items
    public static final RegistryObject<Item> ELVEN_KATANA = ITEMS.register("elven_katana",
            () -> new ElvenKatana(Tiers.DIAMOND, 5, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> OVERSIZED = ITEMS.register("oversized",
            () -> new Oversized(Tiers.DIAMOND, 10, -3.3f, new Item.Properties()));

    //Creative Tab Items
    public static final RegistryObject<Item> DRAKEN_TAB = ITEMS.register("draken_tab",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HAYATE_TAB = ITEMS.register("hayate_tab",
            () -> new Item(new Item.Properties()));

    //Debug
    public static final RegistryObject<Item> MAGIC_DEBUG = ITEMS.register("magic_debug",
            () -> new MagicDebug(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
