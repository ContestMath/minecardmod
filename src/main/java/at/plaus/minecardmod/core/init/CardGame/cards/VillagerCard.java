package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.client.ClientDeckData;
import at.plaus.minecardmod.core.init.CardGame.*;
import at.plaus.minecardmod.core.init.CardGame.events.CardSelectedEvent;
import org.antlr.v4.runtime.misc.Triple;

import java.util.ArrayList;

public class VillagerCard extends Card {
    Card option = new VillagerOption();
    public VillagerCard() {
        super(
                1,
                "textures/gui/villager_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.villager"},
                "Villager");
    }

    private CardSelectedEvent event() {
        return (source, card, b) -> {
            Boardstate board = b;
            if (card.getOwedHalveBoard(board).emeraldCount >= 5) {
                if (card.equals(option)) {
                    card.getOwedHalveBoard(board).emeraldCount -= 5;
                    card.getOwedHalveBoard(board).drawCard();

                    board = card.voidd(board);
                    card.getOwedHalveBoard(board).option_selection.add(option);
                    board.addSelectionEvent(event(), getOptionTargets(), source);
                }
            } else {
                card.getOwedHalveBoard(board).option_selection = new ArrayList<>();
                return board;
            }
            return board;
        };}

    @Override
    public Boardstate etb(Boardstate board) {
        this.getOwedHalveBoard(board).emeraldCount += 4;
        if (this.getOwedHalveBoard(board).emeraldCount >= 5) {
            getOwedHalveBoard(board).option_selection.add(option);
            board.addSelectionEvent(event(), getOptionTargets(), this);
        }
        return super.etb(board);
    }

    public static class VillagerOption extends OptionsCard {
        public VillagerOption() {
            super("textures/gui/villager_card.png", new String[]{"tooltip.minecardmod.cards.villager1"}, "Draw");
        }
    }
}
