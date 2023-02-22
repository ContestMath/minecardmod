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
        board.selectionTargets.add(board.own.meleeBoard);
        board.selectionTargets.add(board.own.rangedBoard);
        board.selectionTargets.add(board.own.specialBoard);
        board.selectionTargets.add(board.enemy.meleeBoard);
        board.selectionTargets.add(board.enemy.rangedBoard);
        board.selectionTargets.add(board.enemy.specialBoard);
        board.selectionListeners.add((card) -> card.damage(3, new Boardstate(board)));
        return super.etb(board);
    }
}
