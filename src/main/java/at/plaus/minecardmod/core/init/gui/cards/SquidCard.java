package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardSubtypes;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
            board.playCard(new CthulhuCard(), board.getPlayerFromHalveBoard(getOwedHalveBoard(board)));
        }
        return super.die(board);
    }

}
