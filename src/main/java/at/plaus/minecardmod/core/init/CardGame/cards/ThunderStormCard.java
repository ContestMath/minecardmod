package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class ThunderStormCard extends Card {
    public ThunderStormCard() {
        super(
                0,
                "textures/gui/thunder_storm_card.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.thunder_storm"},
                "Thunder Storm"
        );
        this.subtypes.add(CardSubtypes.Lightning);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate newBoard = board;
        for (Card card:board.enemy.getAllCardsOnBoard()) {
            newBoard = card.damage(3, newBoard);
        }
        for (Card card:board.own.getAllCardsOnBoard()) {
            newBoard = card.damage(3, newBoard);
        }
        return super.etb(newBoard);
    }
}
