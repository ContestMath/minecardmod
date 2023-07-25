package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

public interface CardDamagedEvent {
    Boardstate onDamaged(int damage, Card card, Boardstate board);
}
