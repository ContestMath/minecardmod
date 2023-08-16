package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import at.plaus.minecardmod.core.init.HideClientside;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;


public class OpenScreenS2CPacket {
    private final int x;
    public OpenScreenS2CPacket(int x) {
        this.x = x;
    }

    public OpenScreenS2CPacket(FriendlyByteBuf buf) {
        this.x = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(x);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            HideClientside.openMinecardScreen(x);
        });
        return true;
    }

}

