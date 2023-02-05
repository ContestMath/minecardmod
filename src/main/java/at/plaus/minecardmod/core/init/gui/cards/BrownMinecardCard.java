package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.MinecardCard;
import net.minecraft.util.text.TranslationTextComponent;

public class BrownMinecardCard extends MinecardCard {

    public BrownMinecardCard() {
        super(
                8,
                "textures/gui/brown_minecard_card.png",
                "Special",
                new TranslationTextComponent("tooltip.minecardmod.cards.brown")
        );
    }
}
