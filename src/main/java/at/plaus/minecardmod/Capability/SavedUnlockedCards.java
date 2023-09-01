package at.plaus.minecardmod.Capability;

import at.plaus.minecardmod.core.init.MinecardRules;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.UnlockedCardsC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import org.stringtemplate.v4.ST;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class SavedUnlockedCards {
    private static String cards;
    private static String tempCards;


    public void copyFrom(SavedUnlockedCards source) {
        this.cards = source.cards;
    }

    public static void setCards(String s) {
        cards = s;
    }

    public static String getCards() {
        return getCards(cards);
    }

    private static String getCards(String s) {
        if (s == null) {
            s = "";
        }
        int cardLen = s.length();
        if (cardLen < Card.getListOfAllCards().size()) {
            for (int i = 0; i < Card.getListOfAllCards().size()-cardLen; i++) {
                s = s + "0";
            }
        }

        return s;
    }

    private static void unlockInner(Class<? extends Card> clazz) {
        List<Character> chars = getCards(tempCards).chars().mapToObj(e->(char)e).collect(Collectors.toList());
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
        tempCards = tempString.toString();
    }

    private static void sendPacket (String s) {
        ModMessages.sendToServer(new UnlockedCardsC2SPacket(s));
    }

    public static void unlock(Class<? extends Card> clazz) {
        unlockInner(clazz);
        sendPacket(tempCards);
    }


    public static void unlock(List<Class<? extends Card>> clazzList) {
        for (Class<? extends Card> clazz:clazzList) {
            unlockInner(clazz);
        }
        sendPacket(tempCards);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("card", getCards());
    }

    public void loadNBTData(CompoundTag nbt) {
        cards = nbt.getString("card");
    }



}
