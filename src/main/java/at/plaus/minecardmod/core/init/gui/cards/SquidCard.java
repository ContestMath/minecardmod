package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
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
    }

}
