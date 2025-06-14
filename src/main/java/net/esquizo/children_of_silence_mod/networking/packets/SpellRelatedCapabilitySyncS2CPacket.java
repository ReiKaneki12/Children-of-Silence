package net.esquizo.children_of_silence_mod.networking.packets;

import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedCapability;
import net.esquizo.children_of_silence_mod.capabilities.spell_related_capability.SpellRelatedProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpellRelatedCapabilitySyncS2CPacket {
    private final CompoundTag nbt;

    public SpellRelatedCapabilitySyncS2CPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public SpellRelatedCapabilitySyncS2CPacket(FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }

    public void toByte(FriendlyByteBuf buf) {
        buf.writeNbt(nbt);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                player.getCapability(SpellRelatedProvider.SPELL_DATA).ifPresent(cap -> {
                    if (cap instanceof SpellRelatedCapability realCap) {
                        realCap.deserializeNBT(nbt);
                    }
                });
            }
        });
        context.setPacketHandled(true);
    }
}
