package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.core.init.CardGame.CardTypes;
import at.plaus.minecardmod.core.init.CardGame.events.EndOfRoundEvent;
import at.plaus.minecardmod.core.init.CardGame.events.StartOfTurnEvent;
import net.minecraft.util.Tuple;

public class RedstoneComputerCard extends Card {

    public RedstoneComputerCard() {
        super(
                0,
                "textures/gui/redstone_computer_card.png",
                CardTypes.RANGED,
                new String[]{"tooltip.minecardmod.cards.redstone_computer"},
                "Redstone Computer"
        );
        emeraldCost = 5;
    }
    private static final StartOfTurnEvent event = event();

    @Override
    public Boardstate etb(Boardstate board) {
        board.startOfTurnListeners.add(new Tuple<>(event,this));
        board.endOfRoundListeners.add(new Tuple<>(removalEvent(),this));
        return super.etb(board);
    }

    private static StartOfTurnEvent event() {
        return (b, c) -> {
            c.getOwedHalveBoard(b).emeraldCount += 3;
            return b;
        };
    }

    private EndOfRoundEvent removalEvent() {
        return (b, s) -> {
            b.removeEvent(event, b.startOfTurnListeners);
            return b;
        };
    }
}
