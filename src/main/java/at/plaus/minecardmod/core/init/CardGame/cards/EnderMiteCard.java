package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

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
