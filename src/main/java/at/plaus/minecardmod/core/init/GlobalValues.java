package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.MinecardBoardState;
import at.plaus.minecardmod.core.init.gui.MinecardCard;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.List;

public class GlobalValues {
    public static HashMap<Player, BothBordstates> savedBoardTemp = new HashMap<Player, BothBordstates>();
    public static HashMap<Player, MinecardBoardState[]> savedBoard1 = new HashMap<Player, MinecardBoardState[]>();
    public static HashMap<Player, MinecardBoardState[]> savedBoard2 = new HashMap<Player, MinecardBoardState[]>();
    public static HashMap<Player, MinecardBoardState[]> savedBoard3 = new HashMap<Player, MinecardBoardState[]>();
    public static HashMap<Player, List<MinecardCard>> deck1 = new HashMap<Player, List<MinecardCard>>();
    public static boolean switchToOther;

}
