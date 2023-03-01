package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

public class RedDragonCard extends Card {
    public RedDragonCard() {
        super(
                12,
                "textures/gui/red_dragon.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.red_dragon"},
                "Red dragon");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.gamePaused = true;
        board.selectionTargets.add(board.own.hand);
        board.selectionListeners.add(Card::discard);
        return super.etb(board);
    }
}
