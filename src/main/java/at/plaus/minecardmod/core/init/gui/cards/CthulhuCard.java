package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardSubtypes;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
