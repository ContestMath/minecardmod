package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

public class BatCard extends Card {

    public BatCard() {
        super(
                2,
                "textures/gui/zombie_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.spy", "tooltip.minecardmod.cards.bat2"},
                "Bat"
        );
        this.isSpy = true;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        if (!this.isOwned(board)) {
            board.own.drawCard();
        } else {
            board.enemy.drawCard();
        }

        return super.etb(board);
    }

}
