package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class ThunderStormCard extends Card {
    public ThunderStormCard() {
        super(
                0,
                "textures/gui/thunder_storm.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.thunder_storm"},
                "Thunder Storm"
        );
        this.subtypes.add(CardSubtypes.Lightning);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);
        for (Card card:board.enemy.getAllCardsOnBoard()) {
            newBoard = card.damage(3, new Boardstate(newBoard));
        }
        for (Card card:board.own.getAllCardsOnBoard()) {
            newBoard = card.damage(3, new Boardstate(newBoard));
        }
        return super.etb(newBoard);
    }
}
