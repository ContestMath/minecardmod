package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardMechanicSymbol;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class BucketCard extends Card {
    public BucketCard() {
        super(
                0,
                "textures/gui/bucket.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.bucket"},
                "Bucket");
        emeraldCost = 1;
    }

    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate tempBoard = new Boardstate(board);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Emerald);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Strength);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Draw);
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (symbol == CardMechanicSymbol.Emerald) {
                boardstate.addSelectionEvent(
                        (source, selected, b) -> {
                            b = selected.voidd(b);
                            return b.appearCard(selected.getNew(), selected.getOwedHalveBoard(b));
                        },
                        getTargets(),
                        this
                );
            }
            if (symbol == CardMechanicSymbol.Strength) {
                boardstate.addSelectionEvent(
                        (source, selected, b) -> selected.damage(5, b),
                        getTargets(),
                        this
                );
            }
            if (symbol == CardMechanicSymbol.Strength) {
                for (Card card:boardstate.getAllCardsOnBoard()) {
                    card.isOnFire = false;
                }
            }
            return boardstate;
        });
        return tempBoard;
    }
}
