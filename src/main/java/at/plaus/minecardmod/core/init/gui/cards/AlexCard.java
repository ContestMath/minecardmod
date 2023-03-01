package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class AlexCard extends Card {

    public AlexCard() {
        super(
                15,
                "textures/gui/alex_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.alex"},
                "Steve"
        );
        isHero = true;
    }

}
