package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;

import java.util.List;

public interface FindTargetsEvent {
    List<Card> onFindTargets(Card source, Boardstate b);
}
