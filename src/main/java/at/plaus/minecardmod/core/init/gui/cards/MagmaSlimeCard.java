package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class MagmaSlimeCard extends Card {

    public MagmaSlimeCard() {
        super(
                2,
                "textures/gui/magma_slime_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.magma_slime"},
                "Magma Slime"
        );
    }

    @Override
    public Boardstate atTheStartOfTurn(Boardstate board) {
        this.strength += 3;
        return super.atTheStartOfTurn(board);
    }
}
