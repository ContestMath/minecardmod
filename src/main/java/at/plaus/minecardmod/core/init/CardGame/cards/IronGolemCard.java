package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
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
        subtypes.add(CardSubtypes.ARTIFACT);
        this.emeraldCost = 7;
        this.resistance = 4;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) ->
                (source.fight(boardstate, card)),
                getTargets(),
                this
                );
        return super.etb(board);
    }
}
