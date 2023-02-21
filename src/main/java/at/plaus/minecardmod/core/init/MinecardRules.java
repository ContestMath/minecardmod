package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.core.init.gui.Card;

import java.util.List;
import java.util.Objects;

public class MinecardRules {
    public static int startingHandsize = 6;

    public boolean isDeckLegal(List<Card> deck) {
        int numberOfCreatures = 0;
        for (Card card:deck) {
            if (Objects.equals(card.type, "Melee") || Objects.equals(card.type, "Ranged") || Objects.equals(card.type, "Special")) {
                numberOfCreatures ++;
            }
        }
        if (numberOfCreatures > 29) {
            return true;
        }
        return false;
    }
}
