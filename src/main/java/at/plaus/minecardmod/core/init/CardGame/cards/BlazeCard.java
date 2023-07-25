package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

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
