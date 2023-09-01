package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.Capability.DeckProvider;
import at.plaus.minecardmod.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GiveEmeraldC2SPacket {
    int x;
    public GiveEmeraldC2SPacket(int x) {
        this.x = x;
    }

    public GiveEmeraldC2SPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(x);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            player.addItem(new ItemStack(Items.EMERALD, x));


        });
        return true;
    }


}

