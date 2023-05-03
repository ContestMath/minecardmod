package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.Capability.DeckProvider;
import at.plaus.minecardmod.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DeckC2SPacket {
    private String deckString;
    private int x;

    public DeckC2SPacket(String deck, int x) {
        this.deckString = deck;
        this.x = x;
    }

    public DeckC2SPacket(FriendlyByteBuf buf) {
        this.deckString = buf.readUtf();
        this.x = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(deckString);
        buf.writeInt(x);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if (x == 1) {
                player.getCapability(DeckProvider.PlayerDeck1).ifPresent(deck -> {
                    deck.setDeck(deckString);
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 1), player);
                });
            }
            if (x == 2) {
                player.getCapability(DeckProvider.PlayerDeck2).ifPresent(deck -> {
                    deck.setDeck(deckString);
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 2), player);
                });
            }
            if (x == 3) {
                player.getCapability(DeckProvider.PlayerDeck3).ifPresent(deck -> {
                    deck.setDeck(deckString);
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 3), player);
                });
            }
            if (x == 4) {
                player.getCapability(DeckProvider.PlayerDeck4).ifPresent(deck -> {
                    deck.setDeck(deckString);
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 4), player);
                });
            }


        });
        return true;
    }


}

