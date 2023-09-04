package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class EndermanCard extends Card {
    public EndermanCard() {
        super(
                9,
                "textures/gui/enderman_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.enderman"},
                "Enderman");
    }

    @Override
    public Boardstate selected(Boardstate board) {
        if (board.getAllCardsOnBoard().contains(this)) {
            board.cancelSelection();
            return returnToHand(board);
        } else {
            return super.selected(board);
        }
    }
}
