package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

public class SkeletonCard extends Card {
    public SkeletonCard() {
        super(
                4,
                "textures/gui/skeleton_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.skeleton"},
                "Skeleton");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.damage(3, boardstate, source),
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
