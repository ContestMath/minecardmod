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

    public DeckC2SPacket(String deck) {
        this.deckString = deck;
    }

    public DeckC2SPacket(FriendlyByteBuf buf) {
        this.deckString = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(deckString);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            player.getCapability(DeckProvider.PlayerDeck0).ifPresent(deck -> {
                deck.setTempDeck(deckString);
                ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getTempDeck()), player);
            });
        });
        return true;
    }


}

