package at.plaus.minecardmod.core.init.gui;


import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.cards.*;
import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
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
    public boolean isHero = false;
    public boolean isSpy = false;
    public boolean hasActiveSelection = false;
    public boolean isToken = false;
    public int emeraldCost = 0;
    public boolean isSwift = false;
    public int resistance = 0;
    public boolean undieing = false;
    public List<CardSubtypes> subtypes = new ArrayList<>();

    public ResourceLocation frame = new ResourceLocation(Minecardmod.MOD_ID,"textures/gui/frame.png");

    public Card(int strength, String texture, CardTypes type, String[] tooltip, String name) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        this.tooltip = tooltip;
        this.name = name;
    }

    public boolean isPlayable(Boardstate board) {
        return getOwedHalveBoard(board).emeraldCount >= emeraldCost;
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
            case STEVE:
                return new SteveCard();
            case ALEX:
                return new AlexCard();
            case LIGHTING_STRIKE:
                return new LightningStrikeCard();
            case ENDER_MITE:
                return new EnderMiteCard();
            case GIANT:
                return new GiantCard();
            case SQUID:
                return new SquidCard();
            case PICKAXE:
                return new PickaxeCard();
            case LIGHTNING_STORM:
                return new LightningStormCard();
            case VILLAGER:
                return new VillagerCard();
            case IRON_GOLEM:
                return new IronGolemCard();
            case CTHULHU:
                return new CthulhuCard();
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

    public HalveBoardState getOwedHalveBoard(Boardstate board) {
        for (Card card:board.own.hand) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        for (Card card:board.own.meleeBoard) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        for (Card card:board.own.rangedBoard) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        for (Card card:board.own.specialBoard) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        for (Card card:board.own.graveyard) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        for (Card card:board.own.deck) {
            if (card.equals(this)) {
                return board.own;
            }
        }
        return board.enemy;
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

    public static String getIdStringFromCardName(CardNames s) {
        int id = getIdFromCardName(s);
        int numberZeros = 3 - Integer.toString(id).length();
        StringBuilder toreturn = new StringBuilder();
        for (int i=0; i<=numberZeros; i++) {
            toreturn.append(0);
        }
        toreturn.append(id);
        return String.valueOf(toreturn);
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
        Boardstate tempBoard =  new Boardstate(board);
        if (x > resistance) {
            strength -= x - resistance;
            for (CardDamagedEvent listener:board.damageListeners) {
                tempBoard = listener.onDamaged(x, this, new Boardstate(tempBoard));
            }
            if (strength <= 0) {
                tempBoard = this.removeFromBoard(tempBoard);
                tempBoard = this.die(tempBoard);
            }
        }
        return tempBoard;
    }

    public Boardstate selected(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);

        newBoard = newBoard.selectionListeners.get(0).onCardSelected(board.selectionSource, this, newBoard);

        newBoard.selectionListeners.remove(0);
        if (newBoard.selectionListeners.isEmpty()) {
            newBoard.gamePaused = false;
            newBoard.selectionListeners = new ArrayList<>();
            newBoard.selectionTargets = new ArrayList<>();
        }

        return newBoard;
    }

    public Boardstate discard(Boardstate boardstate) {
        Boardstate newBoard = new Boardstate(boardstate);
        if (this.isOwned(newBoard)) {
            newBoard.own.graveyard.add(getCardFromName(this.getNameFromCard()));
        } else {
            newBoard.enemy.graveyard.add(getCardFromName(this.getNameFromCard()));
        }
        this.removeFromBoard(newBoard);
        return newBoard;
    }


    public Boardstate die(Boardstate board) {
        return board;
    }

    public Boardstate removeFromBoard(Boardstate board) {
        getOwedHalveBoard(board).graveyard.add(this);
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

