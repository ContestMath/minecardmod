package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class BrownMinecardCard extends MinecardCard {

    public BrownMinecardCard() {
        super(
                8,
                "textures/gui/brown_minecard_card.png",
                CardTypes.SPECIAL,
                "tooltip.minecardmod.cards.brown",
                "Brown card");
    }
}
