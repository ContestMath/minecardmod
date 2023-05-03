package at.plaus.minecardmod.networking;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.networking.packet.*;
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

        net.messageBuilder(BoardC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(BoardC2SPacket::new)
                .encoder(BoardC2SPacket::toBytes)
                .consumerMainThread(BoardC2SPacket::handle)
                .add();

        net.messageBuilder(BoardSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BoardSyncS2CPacket::new)
                .encoder(BoardSyncS2CPacket::toBytes)
                .consumerMainThread(BoardSyncS2CPacket::handle)
                .add();

        net.messageBuilder(UnlockedCardsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(UnlockedCardsSyncS2CPacket::new)
                .encoder(UnlockedCardsSyncS2CPacket::toBytes)
                .consumerMainThread(UnlockedCardsSyncS2CPacket::handle)
                .add();

        net.messageBuilder(UnlockedCardsC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UnlockedCardsC2SPacket::new)
                .encoder(UnlockedCardsC2SPacket::toBytes)
                .consumerMainThread(UnlockedCardsC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
