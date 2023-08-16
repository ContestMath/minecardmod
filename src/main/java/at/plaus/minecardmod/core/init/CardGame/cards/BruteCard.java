package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class BruteCard extends Card {
    public BruteCard() {
        super(
                10,
                "textures/gui/brute_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.brute"},
                "Brute");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) ->
        source.fight(boardstate, card),

        getTargets(),

        this
        );
        return super.etb(board);
    }
}
