package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class PhantomSwarmCard extends Card {

    public PhantomSwarmCard() {
        super(
                3,
                "textures/gui/phantom_swarm.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.phantom_swarm"},
                "Phantom Swarm"
        );
    }

    private final Tuple<EtbEvent, Card> summonEvent = new Tuple<>((card, b, source) -> {
        if (!(card.getOwedHalveBoard(b) == source.getOwedHalveBoard(b))) {
            return b.playCard(new PhantomCard(), source.getOwedHalveBoard(b));
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
