package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class PickaxeCard extends Card {
    public PickaxeCard() {
        super(
                0,
                "textures/gui/pickaxe_card.png",
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
