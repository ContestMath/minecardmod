package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class LightningStormCard extends Card {
    public LightningStormCard() {
        super(
                0,
                "textures/gui/lighting_storm.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.lighting_storm"},
                "Lighting Storm");
    }

    @Override
    public Boardstate etb(Boardstate board) {

        for (Card card:board.enemy.getAllCards()) {
            card.damage(3, board);
        }
        for (Card card:board.own.getAllCards()) {
            card.damage(3, board);
        }

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
