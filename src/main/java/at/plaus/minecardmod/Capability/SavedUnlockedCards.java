package at.plaus.minecardmod.Capability;

import at.plaus.minecardmod.core.init.MinecardRules;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.UnlockedCardsC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class SavedUnlockedCards {
    private static String cards;


    public void copyFrom(SavedUnlockedCards source) {
        this.cards = source.cards;
    }

    public static void setCards(String s) {
        cards = s;
    }

    public static String getCards() {
        if (cards == null) {
            cards = "";
        }
        int cardLen = cards.length();
        if (cardLen < Card.getListOfAllCards().size()) {
            for (int i = 0; i < Card.getListOfAllCards().size()-cardLen; i++) {
                cards = cards + "0";
            }
        }

        return cards;
    }

    public static void unlock(Class<? extends Card> clazz) {
        List<Character> chars = getCards().chars().mapToObj(e->(char)e).collect(Collectors.toList());
        StringBuilder tempString = new StringBuilder();
        int index = Card.getFromClass(clazz).getId();
        int numberUnlocked = chars.get(index) - '0';
        if (numberUnlocked < MinecardRules.maxNumberOfCardsUnlocked) {
            chars.remove(index);
            chars.add(index, Character.forDigit(numberUnlocked+1, 10));
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("New Card: " + Card.getFromClass(clazz).getNew().name + " (current count: " + String.valueOf(numberUnlocked+1) + ")"));
        }
        for (char c:chars) {
            tempString.append(c);
        }
        ModMessages.sendToServer(new UnlockedCardsC2SPacket(tempString.toString()));
    }

    public static Stack<Card> stringToDeck(String s) {
        Stack<Card> deck =  new Stack<>();
        StringBuilder tempString = new StringBuilder();
        List<Character> chars = s.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        int i = 0;

        for (Character character:chars) {
            tempString.append(character);
            i++;
            if ((i % 4) == 0) {
                deck.push(Card.getCardFromId(Integer.parseInt(tempString.toString())));
                i = 0;
                tempString = new StringBuilder();
            }
        }
        return deck;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("card", getCards());
    }

    public void loadNBTData(CompoundTag nbt) {
        cards = nbt.getString("card");
    }



}
