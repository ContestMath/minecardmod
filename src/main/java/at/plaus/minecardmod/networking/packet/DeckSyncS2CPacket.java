package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.client.ClientDeckData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class DeckSyncS2CPacket {
    private final String deck;
    private final int x;

    public DeckSyncS2CPacket(String deck, int x) {
        this.deck = deck;
        this.x = x;
    }

    public DeckSyncS2CPacket(FriendlyByteBuf buf) {
        this.deck = buf.readUtf();
        this.x = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(deck);
        buf.writeInt(x);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (x == 1) {
                ClientDeckData.set1(deck);
            } else if (x == 2) {
                ClientDeckData.set2(deck);
            } else if (x == 3) {
                ClientDeckData.set3(deck);
            } else if (x == 4) {
                ClientDeckData.set4(deck);
            }
        });
        return true;
    }

}

