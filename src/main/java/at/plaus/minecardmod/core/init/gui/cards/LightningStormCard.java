package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardSubtypes;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class LightningStormCard extends Card {
    public LightningStormCard() {
        super(
                0,
                "textures/gui/lighting_storm.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.lighting_storm"},
                "Lighting Storm"
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
