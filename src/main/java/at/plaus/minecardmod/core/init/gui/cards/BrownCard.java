package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

public class BrownCard extends Card {

    public BrownCard() {
        super(
                8,
                "textures/gui/brown_minecard_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.brown"},
                "Brown card");
        this.isToken = true;
    }
}
