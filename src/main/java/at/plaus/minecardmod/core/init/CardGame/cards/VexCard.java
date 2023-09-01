package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class VexCard extends Card {

    public VexCard() {
        super(
                1,
                "textures/gui/vex_card.png",
                CardTypes.SPECIAL,
                new String[]{"tooltip.minecardmod.cards.vex"},
                "Vex"
        );
        this.isToken = true;
    }
}
