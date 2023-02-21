package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;

public interface EtbEvent {
    Boardstate onEtb(Card card, Boardstate board);
}
