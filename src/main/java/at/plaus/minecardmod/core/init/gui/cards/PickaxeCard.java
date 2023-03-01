package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class PickaxeCard extends Card {
    public PickaxeCard() {
        super(
                0,
                "textures/gui/pickaxe.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.pickaxe"},
                "Pickaxe");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        this.getOwedHalveBoard(board).emeraldCount += 7;
        return super.etb(board);
    }
}
