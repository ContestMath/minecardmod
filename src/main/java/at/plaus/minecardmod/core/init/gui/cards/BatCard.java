package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.CardNames;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

import java.util.ArrayList;
import java.util.List;

public class BatCard extends MinecardCard {

    public BatCard() {
        super(
                2,
                "textures/gui/zombie_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.bat1", "tooltip.minecardmod.cards.bat2"},
                "Bat"
        );
        this.isSpy = true;
    }

    @Override
    public BothBordstates ETB(BothBordstates board) {
        board.own.drawCard();
        return super.ETB(board);
    }

}
