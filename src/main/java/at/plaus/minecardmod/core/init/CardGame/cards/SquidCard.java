package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class SquidCard extends Card {

    public SquidCard() {
        super(
                1,
                "textures/gui/squid_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.squid"},
                "Squid"
        );
        subtypes.add(CardSubtypes.SQUID);
    }

    @Override
    public Boardstate die(Boardstate board) {
        getOwedHalveBoard(board).cthulhuCounter++;
        if (getOwedHalveBoard(board).cthulhuCounter >= CthulhuCard.SquidsToDie) {
            getOwedHalveBoard(board).cthulhuCounter = 0;
            board.playCard(new CthulhuCard(), getOwedHalveBoard(board));
        }
        return super.die(board);
    }

}
