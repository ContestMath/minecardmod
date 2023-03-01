package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class LightningStrikeCard extends Card {
    public LightningStrikeCard() {
        super(
                0,
                "textures/gui/lighting_strike.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.lighting_strike"},
                "Lighting Strike");
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.gamePaused = true;
        board.selectionTargets.add(board.own.meleeBoard);
        board.selectionTargets.add(board.own.rangedBoard);
        board.selectionTargets.add(board.own.specialBoard);
        board.selectionTargets.add(board.enemy.meleeBoard);
        board.selectionTargets.add(board.enemy.rangedBoard);
        board.selectionTargets.add(board.enemy.specialBoard);
        board.selectionListeners.add((card, boardstate) -> card.damage(8, new Boardstate(boardstate)));
        return super.etb(board);
    }
}
