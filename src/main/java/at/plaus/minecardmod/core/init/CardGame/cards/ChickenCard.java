package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class ChickenCard extends Card {

    public ChickenCard() {
        super(
                1,
                "textures/gui/chicken_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.chicken"},
                "Chicken"
        );
    }

    @Override
    public Boardstate atTheStartOfTurn(Boardstate board) {
        Boardstate newBoard = board;
        newBoard.playCard(new ChickenCard(), getOwedHalveBoard(board));
        return super.atTheStartOfTurn(newBoard);
    }
}
