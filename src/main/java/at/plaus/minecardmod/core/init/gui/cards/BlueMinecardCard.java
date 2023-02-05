package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.MinecardCard;
import net.minecraft.util.text.TranslationTextComponent;

public class BlueMinecardCard extends MinecardCard {

    public BlueMinecardCard() {
        super(
                5,
                "textures/gui/blue_minecard_card.png",
                "Ranged",
                new TranslationTextComponent("tooltip.minecardmod.cards.blue")
        );
    }
}
