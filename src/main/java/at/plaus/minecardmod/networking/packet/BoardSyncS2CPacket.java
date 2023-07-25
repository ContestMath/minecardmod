package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;


public class BoardSyncS2CPacket {
    private final String board;
    public BoardSyncS2CPacket(String board) {

        this.board = board;
    }

    public BoardSyncS2CPacket(FriendlyByteBuf buf) {
        this.board = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(board);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                Boardstate newBoard = (Boardstate) Boardstate.fromString(board);
                MinecardTableGui.board = newBoard.getReverse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });
        return true;
    }

}

