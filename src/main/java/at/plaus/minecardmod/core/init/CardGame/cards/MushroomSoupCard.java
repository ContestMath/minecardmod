package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

public class MushroomSoupCard extends Card {
    public MushroomSoupCard() {
        super(
                0,
                "textures/gui/mushroom_soup_card.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.mushroom_soup2"},
                "mushroom soup");
        this.isToken = true;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) -> {
            card.strength = card.getDefaultStrength() + 3;
            return boardstate;
        },
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
