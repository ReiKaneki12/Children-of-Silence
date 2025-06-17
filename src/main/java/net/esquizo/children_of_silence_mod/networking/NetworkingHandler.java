package net.esquizo.children_of_silence_mod.networking;

import net.esquizo.children_of_silence_mod.ChildrenOfSilence;
import net.esquizo.children_of_silence_mod.networking.packets.OpenSpellMenuC2S;
import net.esquizo.children_of_silence_mod.networking.packets.SpellRelatedCapabilitySyncS2CPacket;
import net.esquizo.children_of_silence_mod.networking.packets.UseSpellC2S;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkingHandler {
    private static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChildrenOfSilence.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int nextId = 0;

    private static int id() {
        return nextId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(SpellRelatedCapabilitySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SpellRelatedCapabilitySyncS2CPacket::new)
                .encoder(SpellRelatedCapabilitySyncS2CPacket::toByte)
                .consumerMainThread(SpellRelatedCapabilitySyncS2CPacket::handle)
                .add();
        INSTANCE.messageBuilder(OpenSpellMenuC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenSpellMenuC2S::new)
                .encoder(OpenSpellMenuC2S::toByte)
                .consumerMainThread(OpenSpellMenuC2S::handle)
                .add();
        INSTANCE.messageBuilder(UseSpellC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UseSpellC2S::new)
                .encoder(UseSpellC2S::toByte)
                .consumerMainThread(UseSpellC2S::handle)
                .add();
    }

    /**
     * Send a packet from client to server
     */
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    /**
     * Send a packet to all players on the server
     */
    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    /**
     * Send a packet to a specific player
     */
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    /**
     * Send a packet to all players near a specific location
     */
    public static <MSG> void sendToPlayersNear(MSG message, ServerPlayer player, double range) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                player.getX(), player.getY(), player.getZ(), range, player.level().dimension())), message);
    }

    /**
     * Send a packet to all players in a dimension
     */
    public static <MSG> void sendToDimension(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> player.level().dimension()), message);
    }
}
