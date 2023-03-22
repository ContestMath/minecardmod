package at.plaus.minecardmod.networking.packet;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.MinecardTableGui;
import at.plaus.minecardmod.networking.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BoardC2SPacket {

    private String boardString;

    public BoardC2SPacket(String board) {
        this.boardString = board;
    }

    public BoardC2SPacket(FriendlyByteBuf buf) {
        this.boardString = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(boardString);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        ServerLevel level = player.getLevel();
        Boardstate board = MinecardTableGui.board;

        context.enqueueWork(() -> {
            for (ServerPlayer otherPlayer:level.players()) {
                if (!otherPlayer.equals(player)) {
                    ModMessages.sendToPlayer(new BoardSyncS2CPacket(boardString), otherPlayer);
                }
            }
        });
        return true;
    }


}

