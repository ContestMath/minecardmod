package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class StrengthPotionCard extends Card {
    public StrengthPotionCard() {
        super(
                0,
                "textures/gui/strength_potion_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.strength_potion"},
                "Strength Potion");
        subtypes.add(CardSubtypes.POTION);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) -> {
            card.addBuff((b, x, c, s) -> x, source);
            return boardstate;
        },
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
