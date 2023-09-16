package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class DiamondSwordCard extends Card {
    public DiamondSwordCard() {
        super(
                0,
                "textures/gui/diamond_sword_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.diamond_sword"},
                "Diamond Sword");
        subtypes.add(CardSubtypes.ARTIFACT);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) -> {
                    card.addBuff((b, x, c, s) -> 7, source);
        return boardstate;
        },
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
