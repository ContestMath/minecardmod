package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.client.ClientDeckData;
import at.plaus.minecardmod.core.init.CardGame.DeckBuilderGui;
import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;

import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class HideClientside {
    public static void openMinecardScreen (int x) {
        String deckString = ClientDeckData.getDeck1();
        if (!Objects.equals(deckString, "") && !Objects.equals(deckString, null)) {
            Tuple<DeckBuilderGui.DeckValidationResult, String> deckValidation = DeckBuilderGui.deckValidation(DeckBuilderGui.stringToDeck(deckString));
            if (deckValidation.getA() != DeckBuilderGui.DeckValidationResult.SUCCESS && !Minecraft.getInstance().player.isCreative()) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Your deck is illegal and cant be taken into battle"));
                return;
            }
            Minecraft.getInstance().setScreen(new MinecardTableGui(x));
        }
    }

    public static void openDeckBuilderScreen () {
        Minecraft.getInstance().setScreen(new DeckBuilderGui());
    }
}
