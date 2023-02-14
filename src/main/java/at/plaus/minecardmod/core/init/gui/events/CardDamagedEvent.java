package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public interface CardDamagedEvent {
    BothBordstates onDamaged(int damage, MinecardCard card, BothBordstates board);
}
