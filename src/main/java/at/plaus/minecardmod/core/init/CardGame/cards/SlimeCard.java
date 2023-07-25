package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class SlimeCard extends Card {

    public SlimeCard() {
        super(
                5,
                "textures/gui/slime_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.slime"},
                "Slime"
        );
    }

    @Override
    public Boardstate atTheStartOfTurn(Boardstate board) {
        this.strength += 1;
        return super.atTheStartOfTurn(board);
    }
}
