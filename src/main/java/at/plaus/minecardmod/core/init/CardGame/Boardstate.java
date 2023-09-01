package at.plaus.minecardmod.core.init.CardGame;

import at.plaus.minecardmod.core.init.MinecardRules;
import at.plaus.minecardmod.core.init.CardGame.events.*;
import net.minecraft.util.Tuple;
import org.antlr.v4.runtime.misc.Triple;

import java.io.*;
import java.util.*;

public class Boardstate implements Serializable {
    public HalveBoardState own;
    public HalveBoardState enemy;
    int creaturesSacrificed = 0;
    public Stack<Triple<CardSelectedEvent, FindTargetsEvent, Card>> selectionStack = new Stack<>();
    public List<CardDamagedEvent> damageListeners = new ArrayList<CardDamagedEvent>();
    public List<Tuple<EtbEvent, Card>> etbListeners = new ArrayList<>();
    public static int loopIndex = 0;
    public HashMap<Card, Card> copyMap = new HashMap<>();

    public List<Card> getAllRenderableCards() {
        List<Card> list = new ArrayList<>();
        list.addAll(getAllCards());
        list.removeAll(enemy.graveyard);
        list.removeAll(own.graveyard);
        list.removeAll(enemy.hand);
        list.removeAll(enemy.deck);
        list.removeAll(own.deck);

        return list;
    }

    public Boardstate getReverse(){
        Boardstate newBoard = this;
        newBoard.enemy = own;
        newBoard.own = enemy;
        return newBoard;
    }

    public Boardstate() {
        this.enemy = new HalveBoardState();
        this.own = new HalveBoardState();
    }

    public Boardstate(Boardstate board) {
        this.enemy = new HalveBoardState(board.enemy);
        this.own = new HalveBoardState(board.own);
        this.damageListeners = new ArrayList<>(board.damageListeners);
        this.etbListeners = new ArrayList<>(board.etbListeners);
        this.creaturesSacrificed = board.creaturesSacrificed;
        this.selectionStack = new Stack<>();
        this.selectionStack.addAll(board.selectionStack);
        for (Card card:board.getAllCards()) {
            this.copyMap.put(card, getAllCards().get(board.getAllCards().indexOf(card)));
        }
        for (Triple<CardSelectedEvent, FindTargetsEvent, Card> triple:board.selectionStack) {
            triple = new Triple<>(triple.a, triple.b, this.copyMap.get(triple.c));
        }
    }

    public Boardstate playCardFromHand(Card card) {
        if (card.isTargetable(this)) {
            for (int x=0; x<card.sacrificeCost;x++) {
                addSelectionEvent(

                        (source, selected, boardstate) -> {
                    Boardstate newBoard = selected.die(boardstate);
                    newBoard.creaturesSacrificed++;
                    if (newBoard.creaturesSacrificed >= source.sacrificeCost) {
                        source.getOwedHalveBoard(boardstate).emeraldCount -= source.emeraldCost;
                        newBoard.creaturesSacrificed = 0;
                        newBoard = newBoard.playCard(source, source.getOwedHalveBoard(boardstate));
                    }
                    return newBoard;
                },

                        (source, b) -> source.getOwedHalveBoard(this).getAllCardsOnBoard(),

                        card
                );
            }
            if (creaturesSacrificed >= card.sacrificeCost) {
                card.getOwedHalveBoard(this).emeraldCount -= card.emeraldCost;
                MinecardTableGui.cardWasPlayed = true;
                return playCard(card, card.getOwedHalveBoard(this));
            }
        }
        MinecardTableGui.cardWasPlayed = false;
        return this;
    }

    public Boardstate playCard(Card card, HalveBoardState side) {
        boolean isOwnBoard = true;
        if (side.equals(enemy)) {
            isOwnBoard = !isOwnBoard;
        }
        if (card.isSpy) {
            isOwnBoard = !isOwnBoard;
        }

        card.getOwedHalveBoard(this).hand.remove(card);

        if (isOwnBoard) {
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                this.own.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                this.own.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                this.own.specialBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPELL)) {
                own.graveyard.add(card);
            }
        } else {
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                this.enemy.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                this.enemy.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                this.enemy.specialBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPELL)) {
                enemy.graveyard.add(card);
            }
        }
        Boardstate tempBoard = this;
        tempBoard = card.etb(tempBoard);

        if (loopIndex < MinecardRules.maxLoopLength) {
            loopIndex ++;
            for (Tuple event:new ArrayList<>(tempBoard.etbListeners)) {
                tempBoard = ((EtbEvent) event.getA()).onEtb(card, tempBoard, (Card) event.getB());
            }
        }

        return tempBoard;
    }

    public Boardstate appearCard(Card card, HalveBoardState side) {
        boolean isOwnBoard = true;
        if (side.equals(enemy)) {
            isOwnBoard = !isOwnBoard;
        }

        card.getOwedHalveBoard(this).hand.remove(card);

        if (isOwnBoard) {
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                this.own.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                this.own.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                this.own.specialBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPELL)) {
                own.graveyard.add(card.getNew());
            }
        } else {
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                this.enemy.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                this.enemy.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                this.enemy.specialBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPELL)) {
                enemy.graveyard.add(card.getNew());
            }
        }

        return this;
    }

    public Boardstate clearBoard() {
        Boardstate newBoard = this;

        for (Card card: getAllCardsOnBoard()) {
            newBoard = card.die(newBoard);
        }

        return newBoard;

    }

    public List<Card> getSelectionTargets () {
        return selectionStack.peek().b.onFindTargets(selectionStack.peek().c, this);
    }

    public Boardstate switchTurn() {
        Boardstate tempBoard = this;
        tempBoard.own.isYourTurn = !tempBoard.own.isYourTurn;
        tempBoard.enemy.isYourTurn = !tempBoard.enemy.isYourTurn;
        for (Card card:getAllCardsOnBoard()) {
            if (card.getOwedHalveBoard(tempBoard).isYourTurn) {
                tempBoard = card.atTheStartOfTurn(tempBoard);
            }
        }
        return tempBoard;
    }

    public void cancelSelection() {
        selectionStack.pop();
        enemy.option_selection = new ArrayList<>();
        own.option_selection = new ArrayList<>();
    }

    public List<Card> getAllCardsOnBoard() {
        List<Card> list = new ArrayList<>();
        list.addAll(own.getAllCardsOnBoard());
        list.addAll(enemy.getAllCardsOnBoard());
        return list;
    }

    public List<Card> getAllCards() {
        List<Card> list = new ArrayList<>();
        for (List<Card> templist: own.getListOfCardList()) {
            list.addAll(templist);
        }
        for (List<Card> templist: enemy.getListOfCardList()) {
            list.addAll(templist);
        }
        return list;
    }

    public void addSelectionEvent(CardSelectedEvent event, FindTargetsEvent targets, Card source) {
        selectionStack.push(new Triple<>(event, targets, source));

        //Boardstate tempBoard = new Boardstate(this);
        if (selectionStack.peek().b.onFindTargets(copyMap.getOrDefault(source, source), this).isEmpty()) {
            cancelSelection();
        }
    }

    public void clearOptions() {
        own.option_selection = new ArrayList<>();
        enemy.option_selection = new ArrayList<>();
    }


    /** Read the object from Base64 string. */
    public static Object fromString( String s ) throws IOException,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    public static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}
