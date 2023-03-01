package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.client.ClientDeckData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class DeckSyncS2CPacket {
    private final String deck;
    public DeckSyncS2CPacket(String deck) {

        this.deck = deck;
    }

    public DeckSyncS2CPacket(FriendlyByteBuf buf) {
        this.deck = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(deck);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientDeckData.set(deck);

        });
        return true;
    }

}

