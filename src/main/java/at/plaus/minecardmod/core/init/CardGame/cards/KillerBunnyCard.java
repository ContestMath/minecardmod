package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class KillerBunnyCard extends Card {
    public KillerBunnyCard() {
        super(
                13,
                "textures/gui/killer_bunny_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.killer_bunny1",
                        "tooltip.minecardmod.cards.killer_bunny2"
                },
                "Killer Bunny");
        this.sacrificeCost = 2;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate newBoard = board;
        for (Card card: board.getAllCardsOnBoard()) {
            if (card.getStrength(board) <= 3) {
                newBoard = card.die(newBoard);
            }
        }
        return super.etb(newBoard);
    }
}
