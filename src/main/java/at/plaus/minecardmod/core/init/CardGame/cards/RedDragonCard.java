package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

public class RedDragonCard extends Card {
    public RedDragonCard() {
        super(
                12,
                "textures/gui/red_dragon_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.red_dragon"},
                "Red dragon");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.discard(boardstate),
                getCardsInHand(),
                this
        );
        return super.etb(board);
    }
}
