package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.*;

import java.util.ArrayList;
import java.util.List;

public class ZombieCard extends MinecardCard {

    public ZombieCard() {
        super(
                2,
                "textures/gui/zombie_card.png",
                CardTypes.MELEE,
                "tooltip.minecardmod.cards.zombie",
                "Zombie"
        );
    }

    @Override
    public BothBordstates ETB(BothBordstates board) {
        List<MinecardCard> zombies = new ArrayList<MinecardCard>();
        List<MinecardCard> negTempList = new ArrayList<MinecardCard>();
        BothBordstates tempBoard = board;

        for (MinecardCard card:board.own.hand) {
            if (card.getNameFromCard().equals(CardNames.ZOMBIE)) {
                negTempList.add(card);
                zombies.add(card);
            }
        }
        board.own.hand.removeAll(negTempList);
        negTempList = new ArrayList<MinecardCard>();

        for (MinecardCard card:board.own.deck) {
            if (card.getNameFromCard().equals(CardNames.ZOMBIE)) {
                negTempList.add(card);
                zombies.add(card);
            }
        }
        board.own.deck.removeAll(negTempList);
        negTempList = new ArrayList<MinecardCard>();
        
        for (MinecardCard z:zombies) {
            tempBoard = tempBoard.playCard(z, BothBordstates.Player.OWN);
        }
        return super.ETB(tempBoard);
    }
}
