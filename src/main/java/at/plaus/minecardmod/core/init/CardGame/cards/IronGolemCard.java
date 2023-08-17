package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class IronGolemCard extends Card {

    public IronGolemCard() {
        super(
                14,
                "textures/gui/iron_golem_card.png",
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
