package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.*;

import java.util.ArrayList;
import java.util.List;

public class ZombieCard extends Card {

    public ZombieCard() {
        super(
                2,
                "textures/gui/zombie_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.zombie"},
                "Zombie"
        );
    }

    @Override
    public Boardstate etb(Boardstate board) {
        List<Card> zombies = new ArrayList<Card>();
        List<Card> negTempList = new ArrayList<Card>();
        Boardstate tempBoard = board;

        for (Card card:board.own.hand) {
            if (card.getNameFromCard().equals(CardNames.ZOMBIE)) {
                negTempList.add(card);
                zombies.add(card);
            }
        }
        board.own.hand.removeAll(negTempList);
        negTempList = new ArrayList<Card>();

        for (Card card:board.own.deck) {
            if (card.getNameFromCard().equals(CardNames.ZOMBIE)) {
                negTempList.add(card);
                zombies.add(card);
            }
        }
        board.own.deck.removeAll(negTempList);
        negTempList = new ArrayList<Card>();

        for (Card z:zombies) {
            tempBoard = tempBoard.playCard(z, Boardstate.Player.OWN);
        }
        return super.etb(tempBoard);
    }
}
