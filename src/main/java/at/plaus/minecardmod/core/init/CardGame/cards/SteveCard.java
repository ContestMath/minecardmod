package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

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
