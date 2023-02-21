package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;

public interface CardDamagedEvent {
    Boardstate onDamaged(int damage, Card card, Boardstate board);
}
