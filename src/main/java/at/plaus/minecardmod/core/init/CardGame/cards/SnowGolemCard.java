package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class SnowGolemCard extends Card {

    public SnowGolemCard() {
        super(
                3,
                "textures/gui/snow_golem_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.snow_golem1",
                        "tooltip.minecardmod.cards.snow_golem2"
                },
                "Snow Golem"
        );
        this.emeraldCost = 1;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        for (Card card: board.getAllCardsOnBoard()) {
            if (!card.getOwedHalveBoard(board).equals(getOwedHalveBoard(board))) {
                card.damage(1, board, this);
            }
        }
        return super.etb(board);
    }
}
