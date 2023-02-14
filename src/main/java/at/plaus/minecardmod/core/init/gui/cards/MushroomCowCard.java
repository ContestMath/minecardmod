package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class MushroomCowCard extends MinecardCard {

    public MushroomCowCard() {
        super(
                4,
                "textures/gui/wither_skeleton_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.mushroom_cow", "tooltip.minecardmod.cards.mushroom_soup"},
                "Mushroom cow"
        );
    }

}
