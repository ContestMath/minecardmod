package at.plaus.minecardmod.core.init.gui;


import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.cards.*;
import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;
import at.plaus.minecardmod.core.init.gui.events.FindTargetsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.antlr.v4.runtime.misc.Triple;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

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
    public int sacrificeCost = 0;
    public boolean isSwift = false;
    public int resistance = 0;
    public boolean isOnFire = false;
    public boolean undieing = false;
    public List<CardSubtypes> subtypes = new ArrayList<>();
    public String frameString = "textures/gui/frame.png";

    public Card(int strength, String texture, CardTypes type, String[] tooltip, String name) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        this.tooltip = tooltip;
        this.name = name;
    }


    public boolean isTargetable(Boardstate board) {
        int sacrificeTargets = 0;
        for(Card card:board.getAllCardsOnBoard()) {
            if (card.getOwedHalveBoard(board).equals(getOwedHalveBoard(board))) {
                sacrificeTargets ++;
            }
        }

        if (!board.gamePaused) {
            return
                    getOwedHalveBoard(board).emeraldCount >= emeraldCost &&
                            sacrificeTargets >= sacrificeCost
                    ;
        } else {
            return board.getSelectionTargets().contains(this);
        }

    }

    public static List<Class<? extends Card>> getListOfCardClasses() {
        List<Class<? extends Card>> list = new ArrayList<>();
        list.add(BlueCard.class);
        list.add(YellowCard.class);
        list.add(BrownCard.class);
        list.add(ZombieCard.class);
        list.add(WitherSkeletonCard.class);
        list.add(BatCard.class);
        list.add(SkeletonCard.class);
        list.add(CreeperCard.class);
        list.add(MushroomCowCard.class);
        list.add(MushroomSoupCard.class);
        list.add(GlowSquidCard.class);
        list.add(RedDragonCard.class);
        list.add(EnderMiteCard.class);
        list.add(LightningStrikeCard.class);
        list.add(AlexCard.class);
        list.add(SteveCard.class);
        list.add(GiantCard.class);
        list.add(ThunderStormCard.class);
        list.add(PickaxeCard.class);
        list.add(SquidCard.class);
        list.add(VillagerCard.class);
        list.add(IronGolemCard.class);
        list.add(CthulhuCard.class);
        list.add(ChickenCard.class);
        list.add(KillerBunnyCard.class);
        list.add(AllayCard.class);
        list.add(MagmaSlimeCard.class);
        list.add(SnowGolemCard.class);
        list.add(SlimeCard.class);
        list.add(CarpetBombingCard.class);
        list.add(WitchCard.class);
        list.add(BlazeCard.class);
        list.add(BucketCard.class);
        list.add(GuardianCard.class);
        list.add(PhantomCard.class);
        list.add(PhantomSwarmCard.class);


        return list;
    }

    public static List<Card> getListOfAllCards() {
        List<Card> list = new ArrayList<>();
        for (Class<? extends Card> clazz:getListOfCardClasses()) {
            try {
                list.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(Minecardmod.MOD_ID,
                this.texture);
    }

    @NonNull
    public Card getNew() {
        for (Class<? extends Card> clazz:getListOfCardClasses()) {
            if (this.getClass().equals(clazz)) {
                try {
                    return clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
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

    public int getId() {
        for (Class<? extends Card> clazz:getListOfCardClasses()) {
            if (getClass().equals(clazz)) {
               return getListOfCardClasses().indexOf(clazz);
            }
        }
        return -1;
    }

    public String getIdString() {
        int id = getId();
        int numberZeros = 3 - Integer.toString(id).length();
        StringBuilder toreturn = new StringBuilder();
        for (int i=0; i<=numberZeros; i++) {
            toreturn.append(0);
        }
        toreturn.append(id);
        return String.valueOf(toreturn);
    }

    public static Card getCardFromId(int x) {
        return getFromClass(getListOfCardClasses().get(x));
    }


    public static List<Card> getListOfAllNonTokenCards(){
        List<Card> list = new ArrayList<>();
        for (Card card:getListOfAllCards()) {
            if (!card.isToken) {
                list.add(card);
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
                tempBoard = this.die(tempBoard);
            }
        }
        return tempBoard;
    }

    public Boardstate selected(Boardstate board) {
        Boardstate newBoard = new Boardstate(board);
        Triple selectionTriple = newBoard.selectionStack.pop();
        newBoard = ((CardSelectedEvent) selectionTriple.a).onCardSelected((Card) selectionTriple.c, this, newBoard);

        if (newBoard.selectionStack.isEmpty()) {
            newBoard.gamePaused = false;
        }

        return newBoard;
    }

    public Boardstate atTheStartOfTurn(Boardstate board) {
        if (isOnFire) {
            damage(1, board);
        }
        return board;
    }

    public Boardstate discard(Boardstate boardstate) {
        Boardstate newBoard = new Boardstate(boardstate);
        if (this.isOwned(newBoard)) {
            newBoard.own.graveyard.add(getNew());
        } else {
            newBoard.enemy.graveyard.add(getNew());
        }
        this.removeFromBoard(newBoard);
        return newBoard;
    }

    public Boardstate die(Boardstate board) {
        if (board.getAllCards().contains(this)) {
            getOwedHalveBoard(board).graveyard.add(getNew());
            return removeFromBoard(new Boardstate(board));
        }
        return board;
    }

    private Boardstate removeFromBoard(Boardstate board) {
        for (List<Card> list:board.getListOfCardLists()) {
            list.remove(this);
        }
        return board;
    }

    public Boardstate voidd(Boardstate b) {
        getOwedHalveBoard(b).voidd.add(this);
        return removeFromBoard(b);
    }

    public int getDefaultStrength(){
        return getNew().strength;
    }

    public static Card getFromClass(Class<? extends Card> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static FindTargetsEvent getTargets() {
        return (source, board) -> {
            List<Card> list = board.getAllCardsOnBoard();
            list.remove(source);
            return list;
        };
    }
}

