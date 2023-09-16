package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class FireworksCard extends Card {

    public FireworksCard() {
        super(
                0,
                "textures/gui/fireworks_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.fireworks"},
                "Firework"
        );
        emeraldCost = 3;
    }
    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> {
                    for (Card c:card.getOwedHalveBoard(boardstate).deck) {
                        if (c.getClass().equals(FireworksCard.class)) {
                            card.getOwedHalveBoard(boardstate).drawCard(c);
                            break;
                        }
                    }
                    return card.damage(3, boardstate, source);
                },
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
