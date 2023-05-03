package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
