package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

public class WitherSkeletonCard extends Card {

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
