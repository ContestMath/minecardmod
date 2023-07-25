package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

import java.util.List;

public interface FindTargetsEvent {
    List<Card> onFindTargets(Card source, Boardstate b);
}
