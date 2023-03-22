package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class KillerBunnyCard extends Card {
    public KillerBunnyCard() {
        super(
                13,
                "textures/gui/killer_bunny_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.killer_bunny"},
                "Killer Bunny");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);
        for (Card card: board.getAllCardsOnBoard()) {
            if (card.strength <= 3) {
                card.die(board);
            }
        }
        return super.etb(board);
    }
}
