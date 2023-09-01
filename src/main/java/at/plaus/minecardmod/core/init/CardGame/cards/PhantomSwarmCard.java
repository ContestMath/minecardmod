package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.EtbEvent;
import net.minecraft.util.Tuple;

public class PhantomSwarmCard extends Card {

    public PhantomSwarmCard() {
        super(
                3,
                "textures/gui/phantom_swarm_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.phantom_swarm"},
                "Phantom Swarm"
        );
    }

    private final Tuple<EtbEvent, Card> summonEvent = new Tuple<>((card, b, source) -> {
        if (!(card.getOwedHalveBoard(b) == source.getOwedHalveBoard(b))) {
            return b.summon(new PhantomCard(), source.getOwedHalveBoard(b));
        }
        return b;
    },
    this);

    @Override
    public Boardstate etb(Boardstate board) {
        board.etbListeners.add(summonEvent);
        return super.etb(board);
    }

    @Override
    public Boardstate die(Boardstate board) {
        board.etbListeners.remove(summonEvent);
        return super.die(board);
    }
}
