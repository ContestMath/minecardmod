package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.FindTargetsEvent;

import java.util.ArrayList;
import java.util.List;

public class NetherPortalCard extends Card {
    public NetherPortalCard() {
        super(
                0,
                "textures/gui/nether_portal_card.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.nether_portal"},
                "Nether Portal");
        emeraldCost = 1;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> {
                    card.getOwedHalveBoard(board).drawCard(card);
                    return board;
                },
                getNetherCardsFromDeck(),
                this
        );
        getOwedHalveBoard(board).option_selection.addAll(board.getTargets());
        return super.etb(board);
    }

    private static FindTargetsEvent getNetherCardsFromDeck() {
        return (source, board) -> {
            List<Card> list = new ArrayList<>();
            for (Card card:source.getOwedHalveBoard(board).deck) {
                if (card.subtypes.contains(CardSubtypes.NETHER)) {
                    list.add(card);
                }
            }
            return list;
        };
    }
}
