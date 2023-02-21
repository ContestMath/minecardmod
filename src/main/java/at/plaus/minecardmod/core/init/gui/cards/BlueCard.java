package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
