package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class CarpetBombingCard extends Card {
    public CarpetBombingCard() {
        super(
                0,
                "textures/gui/carpet_bombing_card.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.carpet_bombing"},
                "Carpet Bombing"
        );
        this.sacrificeCost = 1;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.damage(8, new Boardstate(boardstate)),
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
