package at.plaus.minecardmod.core.init.gui;


import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.cards.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MinecardCard {

    public static final int cardheight = 30;
    public static final int cardwidth = 20;
    public final ITextComponent tooltip;
    public final CardTypes type;
    public final int strength;
    public final String texture;
    public final String name;
    public boolean isSpy = false;

    public MinecardCard(int strength, String texture, CardTypes type, String tooltip, String name) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        this.tooltip = new TranslationTextComponent(tooltip);
        this.name = name;
    }

    public static int getCardheight(){
        return cardheight;
    }
    public static int getCardwidth(){
        return cardwidth;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(Minecardmod.MOD_ID,
                this.texture);
    }

    public List<ITextComponent> getTooltip() {
        List<ITextComponent> temptooltip = new ArrayList<ITextComponent>();

        temptooltip.add(new StringTextComponent(this.name));
        temptooltip.add(new StringTextComponent("§o" + type.toString().toLowerCase()));
        temptooltip.add(new StringTextComponent(""));
        temptooltip.add(tooltip);
        temptooltip.add(new StringTextComponent(""));
        temptooltip.add(new StringTextComponent("Strength: ").append(new StringTextComponent(Integer.toString(strength))));

        return temptooltip;
    }

    public int getStrength(){
        return this.strength;
    }
    public CardTypes getType() {
        return type;
    }
    public static int getStrengthFromList(List<MinecardCard> cardList) {
        int total = 0;
        for (MinecardCard i:cardList){
            total += i.getStrength();
        }
        return total;
    }

    public BothBordstates ETB(BothBordstates board) {
        return board;
    }

    public static MinecardCard getCardFromName(CardNames s) {
        switch (s) {
            case YELLOW:
                return new YellowMinecardCard();
            case BLUE:
                return new BlueMinecardCard();
            case BROWN:
                return new BrownMinecardCard();
            case ZOMBIE:
                return new ZombieCard();
            case WHITHER_SKELETON:
                return new WitherSkeletonCard();
        }
        return null;
    }

    public CardNames getNameFromCard() {
        for (CardNames name:CardNames.values()) {
            if (Objects.equals(this.name, Objects.requireNonNull(getCardFromName(name)).name)) {
                return name;
            }
        }
        return null;
    }

    public static MinecardCard getCardFromId(int i) {
        return getCardFromName(CardNames.values()[i]);
    }

    public static int getIdFromCardName(CardNames s) {
        int index = 0;
        for (CardNames cardName:CardNames.class.getEnumConstants()) {
            if (cardName == s) {
               return index;
            }
            index ++;
        }
        return -1;
    }
    public static List<MinecardCard> getListOfAllCards(){
        List<MinecardCard> list = new ArrayList<>();
        for (CardNames cardName:CardNames.class.getEnumConstants()) {
            list.add(MinecardCard.getCardFromName(cardName));
        }
        return list;
    }
}

