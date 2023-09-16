package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.FindTargetsEvent;

import java.util.ArrayList;
import java.util.List;

public class TreasureMapCard extends Card {
    public TreasureMapCard() {
        super(
                0,
                "textures/gui/treasure_map_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.treasure_map"},
                "Treasure Map");
        emeraldCost = 2;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> {
                    card.getOwedHalveBoard(board).drawCard(card);
                    return board;
                },
                getArtifactCardsFromDeck(),
                this
        );
        getOwedHalveBoard(board).option_selection.addAll(board.getTargets());
        return super.etb(board);
    }

    private static FindTargetsEvent getArtifactCardsFromDeck() {
        return (source, board) -> {
            List<Card> list = new ArrayList<>();
            for (Card card:source.getOwedHalveBoard(board).deck) {
                if (card.subtypes.contains(CardSubtypes.ARTIFACT)) {
                    list.add(card);
                }
            }
            return list;
        };
    }
}
