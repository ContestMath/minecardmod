package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class VindicatorCard extends Card {

    public VindicatorCard() {
        super(
                2,
                "textures/gui/vindicator_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.vindicator"},
                "Vindicator"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board = board.summon(new VexCard(), getOwedHalveBoard(board));
        board = board.summon(new VexCard(), getOwedHalveBoard(board));
        return super.etb(board);
    }
}
