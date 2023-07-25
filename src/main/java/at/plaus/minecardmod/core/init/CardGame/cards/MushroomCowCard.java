package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.Card;

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
