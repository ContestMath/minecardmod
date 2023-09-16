package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

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
        addBuff((b, x, c, s) -> 2, this);
        return super.atTheStartOfTurn(board);
    }
}
