package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class YellowMinecardCard extends MinecardCard {


    public YellowMinecardCard() {
        super(
                3,
                "textures/gui/yellow_minecard_card.png",
                CardTypes.MELEE,
                "tooltip.minecardmod.cards.yellow",
                "Yellow card"
        );
    }
}
