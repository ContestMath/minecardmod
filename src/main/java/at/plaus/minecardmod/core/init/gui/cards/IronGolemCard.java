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
                new String[]{"tooltip.minecardmod.cards.iron_golem1",
                        "tooltip.minecardmod.cards.iron_golem2",
                        "tooltip.minecardmod.cards.iron_golem3"
                },
                "Iron Golem"
        );
        this.emeraldCost = 7;
        this.resistance = 4;
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
        board.selectionCardListeners.push((source, card, boardstate) -> {
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
