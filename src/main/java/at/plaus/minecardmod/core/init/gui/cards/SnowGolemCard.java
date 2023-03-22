package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class SnowGolemCard extends Card {

    public SnowGolemCard() {
        super(
                3,
                "textures/gui/snow_golem.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.snow_golem"},
                "Snow Golem"
        );
        this.emeraldCost = 1;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        for (Card card: board.getAllCardsOnBoard()) {
            if (!card.getOwedHalveBoard(board).equals(getOwedHalveBoard(board))) {
                card.damage(1, board);
            }
        }
        return super.etb(board);
    }
}
