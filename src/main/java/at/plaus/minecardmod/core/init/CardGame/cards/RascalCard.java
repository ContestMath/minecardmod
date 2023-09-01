package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class RascalCard extends Card {

    public RascalCard() {
        super(
                1,
                "textures/gui/rascal_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.spy", "tooltip.minecardmod.cards.rascal"},
                "Rascal"
        );
        isSpy = true;
    }

    @Override
    public Boardstate die(Boardstate board) {
        getOwedHalveBoard(board).getOther(board).emeraldCount += 13;
        return super.die(board);
    }
}
