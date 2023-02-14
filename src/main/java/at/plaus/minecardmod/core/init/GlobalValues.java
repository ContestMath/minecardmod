package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.MinecardBoardState;
import at.plaus.minecardmod.core.init.gui.MinecardCard;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalValues {
    public static HashMap<PlayerEntity, BothBordstates> savedBoardTemp = new HashMap<PlayerEntity, BothBordstates>();
    public static HashMap<PlayerEntity, MinecardBoardState[]> savedBoard1 = new HashMap<PlayerEntity, MinecardBoardState[]>();
    public static HashMap<PlayerEntity, MinecardBoardState[]> savedBoard2 = new HashMap<PlayerEntity, MinecardBoardState[]>();
    public static HashMap<PlayerEntity, MinecardBoardState[]> savedBoard3 = new HashMap<PlayerEntity, MinecardBoardState[]>();
    public static HashMap<PlayerEntity, List<MinecardCard>> deck1 = new HashMap<PlayerEntity, List<MinecardCard>>();
    public static boolean switchToOther;

}
