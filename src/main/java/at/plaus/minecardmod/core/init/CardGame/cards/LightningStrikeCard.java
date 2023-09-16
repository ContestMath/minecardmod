package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class LightningStrikeCard extends Card {
    public LightningStrikeCard() {
        super(
                0,
                "textures/gui/lighting_strike_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.lighting_strike"},
                "Lightning Strike"
        );
        this.subtypes.add(CardSubtypes.Lightning);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.damage(8, boardstate, source),
                getTargets(),
                this
        );
        return super.etb(board);
    }
}
