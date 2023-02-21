package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class GlowSquidCard extends Card {

    public GlowSquidCard() {
        super(
                1,
                "textures/gui/glow_squid_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.glow_squid"},
                "Glow squid"
        );
    }

}
