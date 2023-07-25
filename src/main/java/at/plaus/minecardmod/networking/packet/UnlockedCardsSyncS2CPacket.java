package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.Capability.SavedUnlockedCards;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class UnlockedCardsSyncS2CPacket {
    private final String cards;
    public UnlockedCardsSyncS2CPacket(String cards) {
        this.cards = cards;
    }

    public UnlockedCardsSyncS2CPacket(FriendlyByteBuf buf) {
        this.cards = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(cards);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            SavedUnlockedCards.setCards(cards);
        });
        return true;
    }

}

