package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public class BatCard extends MinecardCard {

    public BatCard() {
        super(
                2,
                "textures/gui/zombie_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.Spy", "tooltip.minecardmod.cards.bat2"},
                "Bat"
        );
        this.isSpy = true;
    }

    @Override
    public BothBordstates etb(BothBordstates board) {
        if (!this.isOwned(board)) {
            board.own.drawCard();
        } else {
            board.enemy.drawCard();
        }

        return super.etb(board);
    }

}
