package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

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
