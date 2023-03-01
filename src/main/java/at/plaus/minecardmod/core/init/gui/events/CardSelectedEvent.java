package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;

public interface CardSelectedEvent {
    Boardstate onCardSelected (Card card, Boardstate boardstate);
}
