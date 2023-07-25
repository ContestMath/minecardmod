package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class PhantomCard extends Card {

    public PhantomCard() {
        super(
                3,
                "textures/gui/phantom_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.phantom"},
                "Phantom"
        );
        this.isToken = true;
    }

}
