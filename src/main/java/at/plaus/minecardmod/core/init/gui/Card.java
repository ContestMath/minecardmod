package at.plaus.minecardmod.core.init.gui;


import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.cards.*;
import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {

    public static final int cardheight = 30;
    public static final int cardwidth = 20;
    public static final int bigCardheight = 120;
    public static final int bigCwidth = 80;

    public final String[] tooltip;
    public final CardTypes type;
    public int strength;
    public final String texture;
    public final String name;
    public boolean isSpy = false;
    public boolean hasActiveSelection = false;
    public boolean isToken = false;

    public ResourceLocation frame = new ResourceLocation(Minecardmod.MOD_ID,"textures/gui/frame.png");

    public Card(int strength, String texture, CardTypes type, String[] tooltip, String name) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        this.tooltip = tooltip;
        this.name = name;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(Minecardmod.MOD_ID,
                this.texture);
    }

    public List<Component> getTooltip1() {
        List<Component> tempTooltip = new ArrayList<Component>();

        tempTooltip.add(Component.literal(this.name));
        tempTooltip.add(Component.literal("§o" + type.toString().toLowerCase()));



        return tempTooltip;
    }

    public List<Component> getTooltip2() {
        List<Component> tempTooltip = new ArrayList<Component>();

        for (String textComponent:tooltip) {
            tempTooltip.add(Component.translatable(textComponent));
        }
        tempTooltip.add(Component.literal(""));
        tempTooltip.add(Component.literal("Strength: ").append(Component.literal(Integer.toString(strength))));


        return tempTooltip;
    }

    public int getStrength(){
        return this.strength;
    }

    public static int getStrengthFromList(List<Card> cardList) {
        int total = 0;
        for (Card i:cardList){
            total += i.getStrength();
        }
        return total;
    }

    public Boardstate etb(Boardstate board) {
        /*
        for (int i = 0; i < board.etbListeners.size(); i++) {
            newBoard = board.etbListeners.get(i).onEtb(this, board);
        }

         */
        //afterEtb(board);
        return board;
    }

    public Boardstate afterEtb(Boardstate board) {
        return board;
    }

    public static Card getCardFromName(CardNames s) {
        switch (s) {
            case YELLOW:
                return new YellowCard();
            case BLUE:
                return new BlueCard();
            case BROWN:
                return new BrownCard();
            case ZOMBIE:
                return new ZombieCard();
            case WHITHER_SKELETON:
                return new WitherSkeletonCard();
            case BAT:
                return new BatCard();
            case SKELETON:
                return new SkeletonCard();
            case CREEPER:
                return new CreeperCard();
            case MUSHROOM_COW:
                return new MushroomCowCard();
            case GLOW_SQUID:
                return new GlowSquidCard();
            case RED_DRAGON:
                return new RedDragonCard();
        }
        return null;
    }

    public boolean isOwned(Boardstate board) {
        for (Card card:board.own.hand) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (Card card:board.own.hand) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (Card card:board.own.meleeBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (Card card:board.own.rangedBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (Card card:board.own.specialBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        return false;
    }

    public int[] getCardPos(Boardstate board){
        int x = 0;
        int y = 0;
        int index = 0;
        for (List<Card> list: board.getListOfCardLists()) {
            if (list.contains(this)) {
                x = index;
                y = list.indexOf(this);
            }
            index++;
        }
        return new int[]{x, y};
    }

    public CardNames getNameFromCard() {
        for (CardNames name:CardNames.values()) {
            if (Objects.equals(this.name, Objects.requireNonNull(getCardFromName(name)).name)) {
                return name;
            }
        }
        return null;
    }

    public static Card getCardFromId(int i) {
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
    public static List<Card> getListOfAllNonTokenCards(){
        List<Card> list = new ArrayList<>();
        for (CardNames cardName:CardNames.class.getEnumConstants()) {
            Card card = getCardFromName(cardName);
            if (!card.isToken) {
                list.add(Card.getCardFromName(cardName));
            }
        }
        return list;
    }

    public Boardstate damage(int x, Boardstate board) {
        strength -= x;
        for (CardDamagedEvent listener:board.damageListeners) {
            listener.onDamaged(x, this, board);
        }


        return board;
    }
    public Boardstate selected(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);

        newBoard.selectionListeners.get(0).onCardSelected(this);

        newBoard.selectionListeners.remove(0);
        if (newBoard.selectionListeners.isEmpty()) {
            newBoard.gamePaused = false;
            newBoard.selectionListeners = new ArrayList<>();
            newBoard.selectionTargets = new ArrayList<>();
        }

        return newBoard;
    }


    public Boardstate die(Boardstate board) {
        return board;
    }

    public Boardstate removeFromBoard(Boardstate board) {
        board.getListOfCardLists().get(0).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(1).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(2).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(3).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(4).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(5).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(6).removeIf(i -> i.equals(this));
        board.getListOfCardLists().get(7).removeIf(i -> i.equals(this));
        return board;
    }

    public boolean equals(Card card, Boardstate board1, Boardstate board2) {
        return Objects.equals(card.name, name) && this.getCardPos(board1) == card.getCardPos(board2);
    }

    public int getDefaultStrength(){
        return Objects.requireNonNull(getCardFromName(this.getNameFromCard())).strength;
    }
}

