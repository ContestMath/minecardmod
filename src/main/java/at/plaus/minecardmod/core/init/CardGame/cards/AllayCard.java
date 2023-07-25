package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class AllayCard extends Card {

    public AllayCard() {
        super(
                2,
                "textures/gui/allay_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.allay"},
                "Allay"
        );
    }

    @Override
    public Boardstate atTheStartOfTurn(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);
        getOwedHalveBoard(newBoard).emeraldCount += 3;
        return super.atTheStartOfTurn(newBoard);
    }
}
