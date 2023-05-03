package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class BlazeCard extends Card {
    public BlazeCard() {
        super(
                4,
                "textures/gui/blaze_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.blaze"},
                "Blaze");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) ->
                {card.isOnFire = true;
                    return board;
                },

        getTargets(),

        this
        );
        return super.etb(board);
    }
}
