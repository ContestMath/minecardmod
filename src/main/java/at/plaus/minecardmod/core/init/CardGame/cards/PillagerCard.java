package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.StrengthBuff;

public class PillagerCard extends Card {

    public PillagerCard() {
        super(
                4,
                "textures/gui/pillager_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.pillager"},
                "Pillager"
        );
    }

    public StrengthBuff buff = (b, x, s, c) -> {
        if (s.getOwedHalveBoard(b) == getOwedHalveBoard(b) && c.type == CardTypes.MELEE) {
            return 2;
        }
        return 0;
    };

    @Override
    public Boardstate etb(Boardstate board) {
        board.addBuff(buff, this);
        return super.etb(board);
    }

    @Override
    public Boardstate die(Boardstate board) {
        board.removeBuff(buff);
        return super.die(board);
    }
}
