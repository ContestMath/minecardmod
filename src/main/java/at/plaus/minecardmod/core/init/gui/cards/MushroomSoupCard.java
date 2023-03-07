package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

public class MushroomSoupCard extends Card {
    public MushroomSoupCard() {
        super(
                0,
                "textures/gui/mushroom_soup.png",
                CardTypes.SPELL,
                new String[]{"tooltip.minecardmod.cards.mushroom_soup2"},
                "mushroom soup");
        this.isToken = true;
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
        board.selectionCardListeners.push((source, card, boardstate) -> {
            card.strength = card.getDefaultStrength() + 3;
            return boardstate;
        });
        return super.etb(board);
    }
}
