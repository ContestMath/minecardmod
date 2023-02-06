package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class WitherSkeletonCard extends MinecardCard {

    public WitherSkeletonCard() {
        super(
                7,
                "textures/gui/wither_skeleton_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.wither_skeleton"},
                "Wither Skeleton"
        );
    }

}
