package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.CardSubtypes;
import at.plaus.minecardmod.core.init.gui.CardTypes;

public class LightningStrikeCard extends Card {
    public LightningStrikeCard() {
        super(
                0,
                "textures/gui/lighting_strike.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.lighting_strike"},
                "Lighting Strike"
        );
        this.subtypes.add(CardSubtypes.Lightning);
    }

    @Override
    public Boardstate etb(Boardstate board) {
        board.gamePaused = true;
        board.selectionCardTargets.add(board.own.meleeBoard);
        board.selectionCardTargets.add(board.own.rangedBoard);
        board.selectionCardTargets.add(board.own.specialBoard);
        board.selectionCardTargets.add(board.enemy.meleeBoard);
        board.selectionCardTargets.add(board.enemy.rangedBoard);
        board.selectionCardTargets.add(board.enemy.specialBoard);
        board.selectionSource = this;
        board.selectionCardListeners.push((source, card, boardstate) -> card.damage(8, new Boardstate(boardstate)));
        return super.etb(board);
    }
}
