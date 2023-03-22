package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardMechanicSymbol;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class VillagerCard extends Card {
    public VillagerCard() {
        super(
                1,
                "textures/gui/villager.png",
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
