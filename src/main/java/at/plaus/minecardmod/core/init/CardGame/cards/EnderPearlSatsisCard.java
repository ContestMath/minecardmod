package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class EnderPearlSatsisCard extends Card {
    public EnderPearlSatsisCard() {
        super(
                0,
                "textures/gui/ender_pearl_stasis_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.ender_pearl_stasis"},
                "Ender Pearl Stasis"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> card.returnToHand(boardstate),
                getTargetsOnOwnedHalveboard(),
                this
        );
        return super.etb(board);
    }
}
