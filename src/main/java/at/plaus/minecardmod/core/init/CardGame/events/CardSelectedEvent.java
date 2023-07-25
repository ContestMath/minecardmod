package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

public interface CardSelectedEvent {
    Boardstate onCardSelected (Card source, Card selected, Boardstate boardstate);
}
