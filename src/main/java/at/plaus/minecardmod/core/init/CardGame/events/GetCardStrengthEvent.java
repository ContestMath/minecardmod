package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

public interface GetCardStrengthEvent {
    int onGetStrength(int damage, Card card, Boardstate board);
}
