package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.EtbEvent;
import net.minecraft.util.Tuple;

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

    private final Tuple<EtbEvent, Card> explodeEvent = new Tuple<>((card, b, source) -> {

        if (card.type == CardTypes.MELEE && this.isOwned(b) && card.isOwned(b)) {
            List<Card> tempMelee = new ArrayList<>(b.own.meleeBoard);
            for (Card card1:tempMelee) {
                b = card1.die(b);
            }
        } else if (card.type == CardTypes.MELEE && !this.isOwned(b) && !card.isOwned(b)) {
            List<Card> tempMelee = new ArrayList<>(b.enemy.meleeBoard);
            for (Card card1:tempMelee) {
                b = card1.die(b);
            }
        }
        return b;
    },
            this
    );

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
