package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public interface CardSelectedEvent {
    BothBordstates onCardSelected (MinecardCard card);
}
