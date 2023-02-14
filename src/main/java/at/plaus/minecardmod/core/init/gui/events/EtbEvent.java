package at.plaus.minecardmod.core.init.gui.events;

import at.plaus.minecardmod.core.init.gui.BothBordstates;
import at.plaus.minecardmod.core.init.gui.MinecardCard;

public interface EtbEvent {
    BothBordstates onEtb(MinecardCard card, BothBordstates board);
}
