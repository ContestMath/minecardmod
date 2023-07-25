package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardMechanicSymbol;

public interface SymbolSelectedEvent {
    Boardstate onSymbolSelected(CardMechanicSymbol selected, Boardstate boardstate);
}
