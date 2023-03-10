package at.plaus.minecardmod.networking;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.networking.packet.DeckC2SPacket;
import at.plaus.minecardmod.networking.packet.DeckSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Minecardmod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(DeckC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DeckC2SPacket::new)
                .encoder(DeckC2SPacket::toBytes)
                .consumerMainThread(DeckC2SPacket::handle)
                .add();

        net.messageBuilder(DeckSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DeckSyncS2CPacket::new)
                .encoder(DeckSyncS2CPacket::toBytes)
                .consumerMainThread(DeckSyncS2CPacket::handle)
                .add();


    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
