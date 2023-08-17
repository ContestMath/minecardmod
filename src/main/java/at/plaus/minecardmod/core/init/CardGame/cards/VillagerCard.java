package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardMechanicSymbol;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;

public class VillagerCard extends Card {
    public VillagerCard() {
        super(
                1,
                "textures/gui/villager_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.villager"},
                "Villager");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.gamePaused = true;
        this.getOwedHalveBoard(board).emeraldCount += 4;
        Boardstate tempBoard = new Boardstate(board);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Emerald);
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (this.getOwedHalveBoard(boardstate).emeraldCount >= 5 && symbol == CardMechanicSymbol.Emerald) {
                this.getOwedHalveBoard(boardstate).emeraldCount -= 5;
                this.getOwedHalveBoard(boardstate).drawCard();
            }
            return boardstate;
        });
        return super.etb(tempBoard);
    }
}
