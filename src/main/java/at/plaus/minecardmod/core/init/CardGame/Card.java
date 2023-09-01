package at.plaus.minecardmod.core.init.CardGame;


import at.plaus.minecardmod.Capability.SavedUnlockedCards;
import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.CardGame.cards.*;
import at.plaus.minecardmod.core.init.CardGame.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.CardGame.events.CardSelectedEvent;
import at.plaus.minecardmod.core.init.CardGame.events.FindTargetsEvent;
import at.plaus.minecardmod.core.init.CardGame.events.StrengthBuff;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
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
    private int strength;
    public final String texture;
    public final String name;
    public boolean isHero = false;
    public boolean isSpy = false;
    public boolean isToken = false;
    public int emeraldCost = 0;
    public int sacrificeCost = 0;
    public int resistance = 0;
    public boolean isOnFire = false;
    public boolean undieing = false;
    public List<CardSubtypes> subtypes = new ArrayList<>();
    public List<StrengthBuff> buffs = new ArrayList<>();
    public String frameString = "textures/gui/frame.png";

    public Card(int strength, String texture, CardTypes type, String[] tooltip, String name) {
        this.strength = strength;
        this.texture = texture;
        this.type = type;
        this.tooltip = tooltip;
        this.name = name;
    }

    public Card copy() {
        Card card = getFromClass(getClass());
        card.strength = strength;
        card.isHero = isHero;
        card.isSpy = isSpy;
        card.resistance = resistance;
        card.isOnFire = isOnFire;
        card.undieing = undieing;
        return card;
    }


    public boolean isTargetable(Boardstate board) {
        int sacrificeTargets = 0;
        for(Card card:board.getAllCards()) {
            if (card.getOwedHalveBoard(board).equals(getOwedHalveBoard(board))) {
                sacrificeTargets ++;
            }
        }

        if (board.selectionStack.isEmpty()) {
            return
                    getOwedHalveBoard(board).emeraldCount >= emeraldCost &&
                            sacrificeTargets >= sacrificeCost
                    ;
        } else {
            return board.getSelectionTargets().contains(this);
        }

    }

    public static List<Tuple<Integer, Class<? extends Card>>> tupleOfCardClasses() {
        List<Tuple<Integer, Class<? extends Card>>> list = new ArrayList<>();
        list.add(new Tuple<>(0, BlueCard.class));
        list.add(new Tuple<>(1, YellowCard.class));
        list.add(new Tuple<>(2, BrownCard.class));
        list.add(new Tuple<>(3,  PhantomSwarmCard.class));
        list.add(new Tuple<>(4, ZombieCard.class));
        list.add(new Tuple<>(5, WitherSkeletonCard.class));
        list.add(new Tuple<>(6, BatCard.class));
        list.add(new Tuple<>(7, SkeletonCard.class));
        list.add(new Tuple<>(8, CreeperCard.class));
        list.add(new Tuple<>(9, MushroomCowCard.class));
        list.add(new Tuple<>(10, MushroomSoupCard.class));
        list.add(new Tuple<>(11, GlowSquidCard.class));
        list.add(new Tuple<>(12, RedDragonCard.class));
        list.add(new Tuple<>(13, EnderMiteCard.class));
        list.add(new Tuple<>(14, LightningStrikeCard.class));
        list.add(new Tuple<>(15, AlexCard.class));
        list.add(new Tuple<>(16, SteveCard.class));
        list.add(new Tuple<>(17, GiantCard.class));
        list.add(new Tuple<>(18, ThunderStormCard.class));
        list.add(new Tuple<>(19, PickaxeCard.class));
        list.add(new Tuple<>(20, SquidCard.class));
        list.add(new Tuple<>(21, VillagerCard.class));
        list.add(new Tuple<>(22, IronGolemCard.class));
        list.add(new Tuple<>(23, CthulhuCard.class));
        list.add(new Tuple<>(24, ChickenCard.class));
        list.add(new Tuple<>(25, KillerBunnyCard.class));
        list.add(new Tuple<>(26, AllayCard.class));
        list.add(new Tuple<>(27, MagmaSlimeCard.class));
        list.add(new Tuple<>(28, SnowGolemCard.class));
        list.add(new Tuple<>(29, SlimeCard.class));
        list.add(new Tuple<>(30, CarpetBombingCard.class));
        list.add(new Tuple<>(31, WitchCard.class));
        list.add(new Tuple<>(32, BlazeCard.class));
        list.add(new Tuple<>(33, BucketCard.class));
        list.add(new Tuple<>(34, GuardianCard.class));
        list.add(new Tuple<>(35, PhantomCard.class));
        list.add(new Tuple<>(36, PiglinCard.class));
        list.add(new Tuple<>(37, BruteCard.class));
        list.add(new Tuple<>(38, EnderPearlSatsisCard.class));
        list.add(new Tuple<>(39, ShulkerCard.class));
        list.add(new Tuple<>(40, EndermanCard.class));
        list.add(new Tuple<>(41, VindicatorCard.class));
        list.add(new Tuple<>(42, VexCard.class));
        list.add(new Tuple<>(43, CopperGolemCard.class));
        list.add(new Tuple<>(44, RascalCard.class));
        list.add(new Tuple<>(45, NetherPortalCard.class));
        list.add(new Tuple<>(46, SpeedrunningCard.class));
        list.add(new Tuple<>(47, StrengthPotionCard.class));
        return list;
    }

    public static List<Tuple<Integer, Class<? extends Card>>> tupleOfNonTokenCardClasses(){
        List<Tuple<Integer, Class<? extends Card>>> list =new ArrayList<>();
        list.addAll(tupleOfCardClasses());
        for (Tuple<Integer, Class<? extends Card>> tuple:tupleOfCardClasses()) {
            try {
                if (tuple.getB().newInstance().isToken) {
                    list.remove(tuple);
                }
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static List<Card> getListOfAllCards() {
        List<Card> list = new ArrayList<>();
        for (Tuple<Integer, Class<? extends Card>> t : tupleOfCardClasses()) {
            try {
                list.add(t.getB().newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(Minecardmod.MOD_ID,
                this.texture);
    }

    public int getNumberUnlocked() {
        return SavedUnlockedCards.getCards().charAt(getId())-'0';
    }

    @NonNull
    public Card getNew() {
        for (Tuple<Integer, Class<? extends Card>> t : tupleOfCardClasses()) {
            if (this.getClass().equals(t.getB())) {
                try {
                    return t.getB().newInstance();
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
        int x = strength;
        for (StrengthBuff buff:buffs) {
            x += buff.buff(this);
        }
        return x;
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
        for (List<Card> list:board.own.getListOfCardList()) {
            for (Card card:list) {
                if (card.equals(this)) {
                    return board.own;
                }
            }
        }
        return board.enemy;
    }

    public int getId() {
        for (Tuple<Integer, Class<? extends Card>> t : tupleOfCardClasses()) {
            if (getClass().equals(t.getB())) {
               return t.getA();
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
        return getFromClass(tupleOfCardClasses().get(x).getB());
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

    public static List<Card> getListOfUnlockedCards() {
        List<Card> list = new ArrayList<>();
        for (Card card:getListOfAllNonTokenCards()) {
            if (card.getNumberUnlocked() > 0) {
                list.add(card);
            }
        }
        return list;
    }

    public Boardstate damage(int x, Boardstate board) {
        Boardstate tempBoard =  board;
        if (x > resistance) {
            strength -= x - resistance;
            for (CardDamagedEvent listener:new ArrayList<>(board.damageListeners)) {
                tempBoard = listener.onDamaged(x, this, tempBoard);
            }
            if (strength <= 0) {
                tempBoard = this.die(tempBoard);
            }
        }
        return tempBoard;
    }

    public void heal(int x, Boardstate board) {
        Boardstate tempBoard =  board;
        if (getDefaultStrength() < x + strength) {
            strength = getDefaultStrength();
        } else {
            strength += x;
        }
    }

    public void heal() {
        strength = getDefaultStrength();
    }

    public Boardstate selected(Boardstate board) {
        Boardstate newBoard = board;
        Triple selectionTriple = newBoard.selectionStack.pop();
        newBoard = ((CardSelectedEvent) selectionTriple.a).onCardSelected((Card) selectionTriple.c, this, newBoard);
        return newBoard;
    }

    public Boardstate atTheStartOfTurn(Boardstate board) {
        if (isOnFire) {
            damage(1, board);
        }
        return board;
    }


    public Boardstate discard(Boardstate boardstate) {
        Boardstate newBoard = boardstate;
        if (this.isOwned(newBoard)) {
            newBoard.own.graveyard.add(getNew());
        } else {
            newBoard.enemy.graveyard.add(getNew());
        }
        this.removeFromBoard(newBoard);
        return newBoard;
    }

    public Boardstate returnToHand(Boardstate board) {
        Boardstate newBoard = board;
        HalveBoardState halve = getOwedHalveBoard(newBoard);
        newBoard = removeFromBoard(newBoard);
        halve.hand.add(getNew());
        return newBoard;
    }

    public Boardstate die(Boardstate board) {
        if (board.getAllCards().contains(this)) {
            Boardstate newBoard = board;
            HalveBoardState halve = getOwedHalveBoard(board);
            newBoard = removeFromBoard(board);
            halve.graveyard.add(getNew());
            return newBoard;
        }
        return board;
    }

    public Boardstate fight(Boardstate board, Card target) {
        Boardstate newBoard = board;
        int x = target.getStrength();
        int y = getStrength();
        newBoard = target.damage(y, newBoard);
        newBoard = damage(x, newBoard);
        return newBoard;
    }

    protected Boardstate removeFromBoard(Boardstate board) {
        for (List<Card> list:getOwedHalveBoard(board).getListOfCardList()) {
            list.remove(this);
        }
        return board;
    }

    public Boardstate voidd(Boardstate b) {
        Boardstate board = b;
        HalveBoardState halve = getOwedHalveBoard(board);
        board = removeFromBoard(board);
        halve.voidd.add(this);
        return board;
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
            return board.getAllCardsOnBoard();
        };
    }

    public static FindTargetsEvent getTargetsOnOwnedHalveboard() {
        return (source, board) -> {
            return source.getOwedHalveBoard(board).getAllCardsOnBoard();
        };
    }

    public static FindTargetsEvent getOptionTargets() {
        return (source, board) -> {
            return source.getOwedHalveBoard(board).option_selection;
        };
    }

}

