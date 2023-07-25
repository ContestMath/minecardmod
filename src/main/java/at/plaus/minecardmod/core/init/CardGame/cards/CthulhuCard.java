package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class CthulhuCard extends Card {

    public static final int SquidsToDie = 4;

    public CthulhuCard() {
        super(
                50,
                "textures/gui/cthulhu_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.cthulhu"},
                "Cthulhu"
        );
        undieing = true;
        subtypes.add(CardSubtypes.SQUID);
        this.isToken = true;
    }

}
