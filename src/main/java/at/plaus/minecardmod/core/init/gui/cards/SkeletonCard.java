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
        board.gamePaused = true;
        board.selectionCardTargets.add(board.own.meleeBoard);
        board.selectionCardTargets.add(board.own.rangedBoard);
        board.selectionCardTargets.add(board.own.specialBoard);
        board.selectionCardTargets.add(board.enemy.meleeBoard);
        board.selectionCardTargets.add(board.enemy.rangedBoard);
        board.selectionCardTargets.add(board.enemy.specialBoard);
        board.selectionSource = this;
        board.selectionCardListeners.push((source, card, boardstate) -> card.damage(3, new Boardstate(boardstate)));
        return super.etb(board);
    }
}
