package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.AfterCardDamagedEvent;
import at.plaus.minecardmod.core.init.CardGame.events.BeforeCardDamagedEvent;
import at.plaus.minecardmod.core.init.CardGame.events.EndOfRoundEvent;
import at.plaus.minecardmod.core.init.CardGame.events.StartOfTurnEvent;
import net.minecraft.util.Tuple;

public class CrossbowCard extends Card {
    public CrossbowCard() {
        super(
                0,
                "textures/gui/crossbow_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.crossbow"},
                "Crossbow");
        subtypes.add(CardSubtypes.ARTIFACT);
    }

    private static final BeforeCardDamagedEvent event = event();

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent((source, card, boardstate) -> {
            boardstate.beforeDamageListeners.add(new Tuple<>(event, this));
            return card.damage(10, boardstate, source);
            },
        getTargets(),
        this
        );
        board.endOfRoundListeners.add(new Tuple<>(removalEvent(), this));
        return super.etb(board);
    }

    private static BeforeCardDamagedEvent event() {
        return (x, c, b, s1, s2) -> {
            if (s1.getClass().equals(FireworksCard.class) && s1.getOwedHalveBoard(b).equals(s2.getOwedHalveBoard(b))) {
                b.tempSpellDamage += 6;
            }
            return b;
        };
    }

    private EndOfRoundEvent removalEvent() {
        return (b, s) -> {
            b.removeEvent(event, b.beforeDamageListeners);
            return b;
        };
    }
}
