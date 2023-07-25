package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class BlueCard extends Card {

    public BlueCard() {
        super(
                5,
                "textures/gui/big_blue_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.blue"},
                "Blue card");
        this.isToken = true;
    }
}
