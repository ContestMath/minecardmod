package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class SpeedrunningCard extends Card {
    public SpeedrunningCard() {
        super(
                0,
                "textures/gui/speedrunning_card.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.speedrunning"},
                "Speedrunning"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.own.drawCard(3);
        board.enemy.drawCard(3);
        return super.etb(board);
    }
}
