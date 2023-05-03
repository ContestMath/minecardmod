package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

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
                (source, card, boardstate) -> card.damage(3, new Boardstate(boardstate)),
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
