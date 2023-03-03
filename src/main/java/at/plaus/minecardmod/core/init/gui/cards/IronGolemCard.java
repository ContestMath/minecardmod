package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class IronGolemCard extends Card {

    public IronGolemCard() {
        super(
                14,
                "textures/gui/iron_golem.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.iron_golem"},
                "Iron Golem"
        );
        this.emeraldCost = 7;
        this.resistance = 4;
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
        board.selectionSource = this;
        board.selectionListeners.add((source, card, boardstate) -> {
            Boardstate newBoard = new Boardstate(boardstate);
            int damageX = source.strength;
            int damageY = card.strength;
            newBoard = card.damage(damageX, new Boardstate(newBoard));
            newBoard = source.damage(damageY, new Boardstate(newBoard));
            return newBoard;
        });
        return super.etb(board);
    }
}
