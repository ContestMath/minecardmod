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

public class MinecardCard {

    public static final int cardheight = 30;
    public static final int cardwidth = 20;

    public final String[] tooltip;
    public final CardTypes type;
    public int strength;
    public final String texture;
    public final String name;
    public boolean isSpy = false;
    public boolean hasActiveSelection = false;

    public ResourceLocation frame = new ResourceLocation(Minecardmod.MOD_ID,"textures/gui/frame.png");

    public MinecardCard(int strength, String texture, CardTypes type, String[] tooltip, String name) {
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

    public List<Component> getTooltip() {
        List<Component> temptooltip = new ArrayList<Component>();

        temptooltip.add(Component.literal(this.name));
        temptooltip.add(Component.literal(this.name));
        temptooltip.add(Component.literal("§o" + type.toString().toLowerCase()));
        temptooltip.add(Component.literal(""));
        for (String textComponent:tooltip) {
            temptooltip.add(Component.translatable(textComponent));
        }
        temptooltip.add(Component.literal(""));
        temptooltip.add(Component.literal("Strength: ").append(Component.literal(Integer.toString(strength))));

        return temptooltip;
    }

    public int getStrength(){
        return this.strength;
    }

    public static int getStrengthFromList(List<MinecardCard> cardList) {
        int total = 0;
        for (MinecardCard i:cardList){
            total += i.getStrength();
        }
        return total;
    }

    public BothBordstates etb(BothBordstates board) {
        /*
        for (int i = 0; i < board.etbListeners.size(); i++) {
            newBoard = board.etbListeners.get(i).onEtb(this, board);
        }

         */
        //afterEtb(board);
        return board;
    }

    public BothBordstates afterEtb(BothBordstates board) {
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
            case BAT:
                return new BatCard();
            case SKELETON:
                return new SkeletonCard();
            case CREEPER:
                return new CreeperCard();
            case MUSHROOM_COW:
                return new MushroomCowCard();
        }
        return null;
    }

    public boolean isOwned(BothBordstates board) {
        for (MinecardCard card:board.own.hand) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (MinecardCard card:board.own.hand) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (MinecardCard card:board.own.meleeBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (MinecardCard card:board.own.rangedBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        for (MinecardCard card:board.own.specialBoard) {
            if (card.equals(this)) {
                return true;
            }
        }
        return false;
    }

    public int[] getCardPos(BothBordstates board){
        int x = 0;
        int y = 0;
        int index = 0;
        for (List<MinecardCard> list: board.getListOfCardLists()) {
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

    public BothBordstates damage(int x, BothBordstates board) {
        strength -= x;
        for (CardDamagedEvent listener:board.damageListeners) {
            listener.onDamaged(x, this, board);
        }

        return board;
    }
    public BothBordstates selected(BothBordstates board) {
        for (CardSelectedEvent listener:board.selectionListeners) {
            listener.onCardSelected(this);
        }
        board.gamePaused = false;
        board.selectionListeners = new ArrayList<CardSelectedEvent>();
        board.selectionTargets = new ArrayList<List<MinecardCard>>();
        return board;
    }


    public BothBordstates die(BothBordstates board) {
        return board;
    }

    public BothBordstates removeFromBoard(BothBordstates board) {
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

    public boolean equals(MinecardCard card, BothBordstates board1, BothBordstates board2) {
        return Objects.equals(card.name, name) && this.getCardPos(board1) == card.getCardPos(board2);
    }
}

