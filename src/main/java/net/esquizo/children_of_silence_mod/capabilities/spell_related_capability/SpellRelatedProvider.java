package net.esquizo.children_of_silence_mod.capabilities.spell_related_capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpellRelatedProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ISpellRelatedCapability> SPELL_DATA = CapabilityManager.get(new CapabilityToken<ISpellRelatedCapability>() {});

    private SpellRelatedCapability spellData = null;
    private final LazyOptional<ISpellRelatedCapability> optional = LazyOptional.of(this::createSpellData);

    private SpellRelatedCapability createSpellData() {
        if (this.spellData == null) {
            this.spellData = new SpellRelatedCapability();
        }
        return this.spellData;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == SPELL_DATA){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createSpellData().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createSpellData().deserializeNBT(tag);
    }
}
