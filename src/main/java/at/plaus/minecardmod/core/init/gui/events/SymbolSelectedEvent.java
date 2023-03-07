package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardMechanicSymbol;

public interface SymbolSelectedEvent {
    Boardstate onSymbolSelected(CardMechanicSymbol selected, Boardstate boardstate);
}
