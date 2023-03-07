package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class SlimeCard extends Card {

    public SlimeCard() {
        super(
                4,
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
