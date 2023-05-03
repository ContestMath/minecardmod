package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardSubtypes;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class WitchCard extends Card {

    public WitchCard() {
        super(
                3,
                "textures/gui/witch_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.witch"},
                "Witch"
        );
    }

    private final Tuple<EtbEvent, Card> potionEvent = new Tuple<>((card, b, source) -> {
        if (card.subtypes.contains(CardSubtypes.POTION)) {
            card.getOwedHalveBoard(b).drawCard();
        }
        return b;
    },
            this
    );

    @Override
    public Boardstate etb(Boardstate board) {
        board.etbListeners.add(potionEvent);
        return super.etb(board);
    }

    @Override
    public Boardstate die(Boardstate board) {
        board.etbListeners.remove(potionEvent);
        return super.die(board);
    }
}
