package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
        Boardstate newBoard = new Boardstate(board);
        newBoard.playCard(new ChickenCard(), newBoard.getPlayerFromHalveBoard(getOwedHalveBoard(newBoard)));
        return super.atTheStartOfTurn(newBoard);
    }
}
