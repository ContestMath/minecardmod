package at.plaus.minecardmod.core.init.gui.cards;

import at.plaus.minecardmod.core.init.gui.MinecardCard;
import net.minecraft.util.text.TranslationTextComponent;

public class YellowMinecardCard extends MinecardCard {


    public YellowMinecardCard() {
        super(
                3,
                "textures/gui/yellow_minecard_card.png",
                "Melee",
                new TranslationTextComponent("tooltip.minecardmod.cards.yellow")
        );
    }
}
