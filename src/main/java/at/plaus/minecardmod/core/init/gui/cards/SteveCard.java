package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class SteveCard extends Card {

    public SteveCard() {
        super(
                15,
                "textures/gui/steve_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.steve"},
                "Steve"
        );
        isHero = true;
    }

}
