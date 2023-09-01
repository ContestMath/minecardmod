package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class ShulkerCard extends Card {
    public ShulkerCard() {
        super(
                3,
                "textures/gui/shulker_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.shulker"},
                "Shulker");
        this.emeraldCost = 2;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.returnToHand(boardstate),
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
