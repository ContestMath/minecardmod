package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class EnderMiteCard extends Card {

    public EnderMiteCard() {
        super(
                1,
                "textures/gui/ender_mite_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.ender_mite"},
                "Ender Mite"
        );
        isHero = true;
    }

    @Override
    public Boardstate die(Boardstate board) {
        Boardstate boardstate = new Boardstate(board);
        this.getOwedHalveBoard(boardstate).drawCard();
        return super.die(boardstate);
    }
}
