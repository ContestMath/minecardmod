package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

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
