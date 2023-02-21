package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.HalveBoardState;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.List;

public class GlobalValues {
    public static HashMap<Player, Boardstate> savedBoardTemp = new HashMap<Player, Boardstate>();
    public static HashMap<Player, HalveBoardState[]> savedBoard1 = new HashMap<Player, HalveBoardState[]>();
    public static HashMap<Player, HalveBoardState[]> savedBoard2 = new HashMap<Player, HalveBoardState[]>();
    public static HashMap<Player, HalveBoardState[]> savedBoard3 = new HashMap<Player, HalveBoardState[]>();
    public static HashMap<Player, List<Card>> deck1 = new HashMap<Player, List<Card>>();
    public static boolean switchToOther;

}
