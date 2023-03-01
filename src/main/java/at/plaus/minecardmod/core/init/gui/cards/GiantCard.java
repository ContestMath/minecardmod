package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class GiantCard extends Card {

    public GiantCard() {
        super(
                16,
                "textures/gui/giant_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.giant"},
                "Giant"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        getOwedHalveBoard(board).getOther(board).drawCard();
        return super.etb(board);
    }
}
