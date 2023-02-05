package at.plaus.minecardmod.core.init.gui;


import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.cards.BlueMinecardCard;
import at.plaus.minecardmod.core.init.gui.cards.BrownMinecardCard;
import at.plaus.minecardmod.core.init.gui.cards.YellowMinecardCard;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MinecardCard {

    public static final int cardheight = 30;
    public static final int cardwidth = 20;
    public final List<ITextComponent> tooltip;
    public final String type;
    public final int strength;
    public final String texture;

    public MinecardCard(int strength, String texture, String type, ITextComponent tooltip) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        List<ITextComponent> temptooltip = new ArrayList<ITextComponent>();
        temptooltip.add(new StringTextComponent(Integer.toString(strength)));
        temptooltip.add(tooltip);
        this.tooltip = temptooltip;
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
        return tooltip;
    }

    public int getStrength(){
        return this.strength;
    }
    public String getType() {
        return type;
    }
    public static int getStrengthFromList(List<MinecardCard> cardList) {
        int total = 0;
        for (MinecardCard i:cardList){
            total += i.getStrength();
        }
        return total;
    }

    public void placementEffect() {
    }

    public static MinecardCard getCardFromName(CardNames s) {
        switch (s) {
            case YELLOW:
                return new YellowMinecardCard();
            case BLUE:
                return new BlueMinecardCard();
            case BROWN:
                return new BrownMinecardCard();
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
        return 0;
    }
    public static List<MinecardCard> getListOfAllCards(){
        List<MinecardCard> list = new ArrayList<>();
        for (CardNames cardName:CardNames.class.getEnumConstants()) {
            list.add(MinecardCard.getCardFromName(cardName));
        }
        return list;
    }
}

