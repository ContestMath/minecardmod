package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;

public class PiglinCard extends Card {
    public PiglinCard() {
        super(
                5,
                "textures/gui/piglin_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.piglin"},
                "Piglin");
    }

private CardSelectedEvent getEvent() {
    return (source, card, boardstate) -> {
        if (card.getOwedHalveBoard(boardstate).emeraldCount >= 3) {
            card.getOwedHalveBoard(boardstate).emeraldCount -= 2;
            boardstate.addSelectionEvent(getEvent(), getTargets(), this);
            return card.damage(3, new Boardstate(boardstate));
        }
        return boardstate;
    };
}


    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(getEvent(), getTargets(), this);
        return super.etb(board);
    }
}
