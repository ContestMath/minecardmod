package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class AlexCard extends Card {

    public AlexCard() {
        super(
                15,
                "textures/gui/alex_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.alex"},
                "Alex"
        );
        isHero = true;
    }

}
