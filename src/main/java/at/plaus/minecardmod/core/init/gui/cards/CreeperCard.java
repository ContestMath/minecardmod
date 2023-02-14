package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.CardTypes;
import at.plaus.minecardmod.core.init.gui.MinecardCard;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;

import java.util.ArrayList;
import java.util.List;

public class CreeperCard extends MinecardCard {

    public CreeperCard() {
        super(
                10,
                "textures/gui/creeper_card.png",
                CardTypes.MELEE,
                new String[]{"tooltip.minecardmod.cards.creeper", "tooltip.minecardmod.cards.creeper2"},
                "Creeper"
        );
        this.isSpy = true;
    }

    private final EtbEvent explodeEvent = (card, b) -> {

        if (card.type == CardTypes.MELEE && this.isOwned(b) && card.isOwned(b)) {
            List<MinecardCard> tempMelee = new ArrayList<>(b.own.meleeBoard);
            for (MinecardCard card1:tempMelee) {
                b = card1.removeFromBoard(b);
            }
            for (MinecardCard card1:tempMelee) {
                b = card1.die(b);
            }
        } else if (card.type == CardTypes.MELEE && !this.isOwned(b) && !card.isOwned(b)) {
            List<MinecardCard> tempMelee = new ArrayList<>(b.enemy.meleeBoard);
            for (MinecardCard card1:tempMelee) {
                b = card1.removeFromBoard(b);
            }
            for (MinecardCard card1:tempMelee) {
                b = card1.die(b);
            }
        }
        return b;
    };

    @Override
    public BothBordstates etb(BothBordstates board) {
        board.etbListeners.add(explodeEvent);
        return super.etb(board);
    }

    @Override
    public BothBordstates die(BothBordstates board) {
        board.etbListeners.remove(explodeEvent);
        return super.die(board);
    }
}
