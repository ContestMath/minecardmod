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
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Draw);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Discard);
        tempBoard.selectionSymbolTargets.add(CardMechanicSymbol.Strength);
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (this.getOwedHalveBoard(boardstate).emeraldCount >= 5 && symbol == CardMechanicSymbol.Emerald) {
                this.getOwedHalveBoard(boardstate).emeraldCount -= 5;
                this.getOwedHalveBoard(boardstate).drawCard();
            }
            return boardstate;
        });
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (this.getOwedHalveBoard(boardstate).emeraldCount >= 5 && symbol == CardMechanicSymbol.Draw) {
                this.getOwedHalveBoard(boardstate).emeraldCount -= 5;
                this.getOwedHalveBoard(boardstate).drawCard();
            }
            return boardstate;
        });
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (this.getOwedHalveBoard(boardstate).emeraldCount >= 5 && symbol == CardMechanicSymbol.Discard) {
                this.getOwedHalveBoard(boardstate).emeraldCount -= 5;
                this.getOwedHalveBoard(boardstate).drawCard();
            }
            return boardstate;
        });
        tempBoard.selectionSymbolListeners.push((symbol, boardstate) -> {
            if (this.getOwedHalveBoard(boardstate).emeraldCount >= 5 && symbol == CardMechanicSymbol.Strength) {
                this.getOwedHalveBoard(boardstate).emeraldCount -= 5;
                this.getOwedHalveBoard(boardstate).drawCard();
            }
            return boardstate;
        });
        return super.etb(tempBoard);
    }
}
