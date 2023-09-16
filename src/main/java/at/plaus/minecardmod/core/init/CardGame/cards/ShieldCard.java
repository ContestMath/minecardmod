package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class ShieldCard extends Card {

    public ShieldCard() {
        super(
                0,
                "textures/gui/shield_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.shield"},
                "Shield"
        );
        subtypes.add(CardSubtypes.ARTIFACT);
        emeraldCost = 1;
    }

}
