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
        board.addSelectionEvent((source, card, boardstate) -> {
            Boardstate newBoard = new Boardstate(boardstate);
            int damageX = source.strength;
            int damageY = card.strength;
            newBoard = card.damage(damageX, new Boardstate(newBoard));
            newBoard = source.damage(damageY, new Boardstate(newBoard));
            return newBoard;
        },
                getTargets(),
                this
                );
        return super.etb(board);
    }
}
