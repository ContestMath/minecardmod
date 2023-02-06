package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class BlueMinecardCard extends MinecardCard {

    public BlueMinecardCard() {
        super(
                5,
                "textures/gui/blue_minecard_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.blue"},
                "Blue card");
    }
}
