package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardSubtypes;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.FindTargetsEvent;

import java.util.ArrayList;
import java.util.List;

public class CopperGolemCard extends Card {
    public CopperGolemCard() {
        super(
                9,
                "textures/gui/copper_golem_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.copper_golem"},
                "Copper Golem");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.addSelectionEvent(
                (source, card, boardstate) -> {
                    card.getOwedHalveBoard(board).drawCard(card);
                    return board;
                },
                getLighningCardsFromDeck(),
                this
        );
        getOwedHalveBoard(board).option_selection.addAll(board.getTargets());
        return super.etb(board);
    }

    private static FindTargetsEvent getLighningCardsFromDeck() {
        return (source, board) -> {
            List<Card> list = new ArrayList<>();
            for (Card card:source.getOwedHalveBoard(board).deck) {
                if (card.subtypes.contains(CardSubtypes.Lightning)) {
                    list.add(card);
                }
            }
            return list;
        };
    }
}
