package net.esquizo.children_of_silence_mod.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class ModKeyMappings {
    public static final String MAGIC_CATEGORY = "key.categories.magic." + ChildrenOfSilence.MOD_ID;

    //Open spell book menu
    public static final KeyMapping SPELL_MENU_KEY = new KeyMapping(
            "key." + ChildrenOfSilence.MOD_ID + ".spell_menu",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_M,
            MAGIC_CATEGORY
    );
    //Use spell
    public static final KeyMapping USE_SPELL_KEY = new KeyMapping(
            "key." + ChildrenOfSilence.MOD_ID + ".use_spell",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_Z,
            MAGIC_CATEGORY
    );
}
