package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.EndOfRoundEvent;
import at.plaus.minecardmod.core.init.CardGame.events.StartOfTurnEvent;
import net.minecraft.util.Tuple;

public class LagMachineCard extends Card {

    public LagMachineCard() {
        super(
                0,
                "textures/gui/lag_machine_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.lag_machine"},
                "Lag Machine"
        );
        emeraldCost = 7;
    }

    private static final StartOfTurnEvent event = event();

    @Override
    public Boardstate etb(Boardstate board) {
        board.startOfTurnListeners.add(new Tuple<>(event,this));
        board.endOfRoundListeners.add(new Tuple<>(removalEvent(),this));
        return super.etb(board);
    }

    private static StartOfTurnEvent event() {
        return (board, card) -> {
            board.addSelectionEvent(
                    (source, selected, boardstate) -> selected.discard(boardstate),
                    getCardsInHand(),
                    card
            );
            return board;
        };
    }

    private EndOfRoundEvent removalEvent() {
        return (b, s) -> {
            b.removeEvent(event, b.startOfTurnListeners);
            return b;
        };
    }
}
