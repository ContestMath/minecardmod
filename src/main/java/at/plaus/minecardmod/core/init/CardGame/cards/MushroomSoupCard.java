package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

public class MushroomSoupCard extends Card {
    public MushroomSoupCard() {
        super(
                0,
                "textures/gui/mushroom_soup_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.mushroom_soup2"},
                "mushroom soup");
        this.isToken = true;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) -> {
                    card.addBuff((b, x, c, s) -> 3, source);
        return boardstate;
        },
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
