package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class YellowCard extends Card {


    public YellowCard() {
        super(
                3,
                "textures/gui/yellow_minecard_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.yellow"},
                "Yellow card"
        );
        this.isToken = true;
    }
}
