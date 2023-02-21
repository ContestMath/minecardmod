package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.Card;

public class MushroomCowCard extends Card {

    public MushroomCowCard() {
        super(
                4,
                "textures/gui/mushroom_cow_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.mushroom_cow", "tooltip.minecardmod.cards.mushroom_soup1"},
                "Mushroom cow"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        if (this.isOwned(board)) {
            board.own.hand.add(new MushroomSoupCard());
        } else {
            board.enemy.hand.add(new MushroomSoupCard());
        }

        return super.etb(board);
    }

}
