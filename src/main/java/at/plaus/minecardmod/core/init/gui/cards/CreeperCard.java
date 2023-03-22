package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;

import java.util.ArrayList;
import java.util.List;

public class CreeperCard extends Card {

    public CreeperCard() {
        super(
                10,
                "textures/gui/creeper_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.spy", "tooltip.minecardmod.cards.creeper"},
                "Creeper"
        );
        this.isSpy = true;
    }

    private final EtbEvent explodeEvent = (card, b) -> {

        if (card.type == CardTypes.MELEE && this.isOwned(b) && card.isOwned(b)) {
            List<Card> tempMelee = new ArrayList<>(b.own.meleeBoard);
            /*
            for (Card card1:tempMelee) {
                b = card1.removeFromBoard(b);
            }

             */
            for (Card card1:tempMelee) {
                b = card1.die(b);
            }
        } else if (card.type == CardTypes.MELEE && !this.isOwned(b) && !card.isOwned(b)) {
            List<Card> tempMelee = new ArrayList<>(b.enemy.meleeBoard);
            /*
            for (Card card1:tempMelee) {
                b = card1.removeFromBoard(b);
            }
             */
            for (Card card1:tempMelee) {
                b = card1.die(b);
            }
        }
        return b;
    };

    @Override
    public Boardstate etb(Boardstate board) {
        board.etbListeners.add(explodeEvent);
        return super.etb(board);
    }

    @Override
    public Boardstate die(Boardstate board) {
        board.etbListeners.remove(explodeEvent);
        return super.die(board);
    }
}
