package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

public interface EtbEvent {
    Boardstate onEtb(Card card, Boardstate board, Card source);
}
