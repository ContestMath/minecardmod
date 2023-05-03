package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;

public class GuardianCard extends Card {



    public GuardianCard() {
        super(
                3,
                "textures/gui/guardian_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.guardian"},
                "Guardian"
        );
    }

    private final CardSelectedEvent getDamageEvent1() {
        return (source, card, b) -> {
        b = card.damage(2, b);
        b.addSelectionEvent(damageEvent2, getTargets(), this);
        return b;
    };}

    private final CardSelectedEvent damageEvent2 = (source, card, b) -> {
        return card.damage(2, b);
    };

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(getDamageEvent1(), getTargets(), this);
        return super.etb(board);
    }

}
