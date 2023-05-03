package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.Capability.UnlockedCardsProvider;
import at.plaus.minecardmod.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UnlockedCardsC2SPacket {
    private final String cards;

    public UnlockedCardsC2SPacket(String deck) {
        this.cards = deck;
    }

    public UnlockedCardsC2SPacket(FriendlyByteBuf buf) {
        this.cards = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(cards);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            player.getCapability(UnlockedCardsProvider.PlayerUnlockedCards).ifPresent(cards -> {
                cards.setCards(this.cards);
                ModMessages.sendToPlayer(new UnlockedCardsSyncS2CPacket(cards.getCards()), player);
            });



        });
        return true;
    }


}

