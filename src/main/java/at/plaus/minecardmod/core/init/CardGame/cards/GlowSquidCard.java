package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class GlowSquidCard extends Card {

    public GlowSquidCard() {
        super(
                1,
                "textures/gui/glow_squid_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.glow_squid"},
                "Glow squid"
        );
        subtypes.add(CardSubtypes.SQUID);
    }

    @Override
    public Boardstate die(Boardstate board) {
        getOwedHalveBoard(board).cthulhuCounter++;
        return super.die(board);
    }
}
