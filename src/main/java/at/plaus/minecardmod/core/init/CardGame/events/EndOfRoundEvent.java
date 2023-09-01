package at.plaus.minecardmod.core.init.CardGame.events;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;

public interface EndOfRoundEvent {
    Boardstate onEndOfRound(Boardstate boardstate);
}
