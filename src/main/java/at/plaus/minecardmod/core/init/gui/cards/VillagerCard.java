package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class VillagerCard extends Card {
    public VillagerCard() {
        super(
                1,
                "textures/gui/villager.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.villager"},
                "Villager");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        this.getOwedHalveBoard(board).emeraldCount += 4;
        return super.etb(board);
    }
}
