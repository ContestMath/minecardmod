package at.plaus.minecardmod.core.init.CardGame;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.io.Serializable;
import java.util.*;

public class HalveBoardState implements Serializable {
    public Stack<Card> deck;
    public List<Card> hand;
    public List<Card> meleeBoard;
    public List<Card> rangedBoard;
    public List<Card> specialBoard;
    public List<Card> graveyard;
    public List<Card> voidd;
    public List<Card> option_selection = new ArrayList<>();
    public boolean isYourTurn = true;
    public boolean hasPassed = false;
    public int lifePoints = 2;
    public int emeraldCount = 0;
    public int cthulhuCounter = 0;


    public HalveBoardState() {
        this.deck = new Stack<Card>();
        this.voidd = new ArrayList<Card>();
        this.hand = new ArrayList<Card>();
        this.meleeBoard = new ArrayList<Card>();
        this.rangedBoard = new ArrayList<Card>();
        this.specialBoard = new ArrayList<Card>();
        this.graveyard = new ArrayList<Card>();
    }

    public HalveBoardState(HalveBoardState boardState) {
        this.isYourTurn = boardState.isYourTurn;
        this.hasPassed = boardState.hasPassed;
        this.lifePoints = boardState.lifePoints;
        Stack<Card> newDeck = new Stack<>();
        newDeck.addAll(boardState.deck);
        this.deck = newDeck;
        this.hand = newCardlist(boardState.hand);
        this.meleeBoard = newCardlist(boardState.meleeBoard);
        this.rangedBoard = newCardlist(boardState.rangedBoard);
        this.specialBoard = newCardlist(boardState.specialBoard);
        this.graveyard = newCardlist(boardState.graveyard);
        this.emeraldCount = boardState.emeraldCount;
        this.cthulhuCounter = boardState.cthulhuCounter;
        this.voidd = boardState.voidd;
        this.option_selection = newCardlist(boardState.option_selection);
    }

    public List<List<Card>> getListOfCardList() {
        List<List<Card>> list = new ArrayList<>();
        list.add(option_selection);
        list.add(hand);
        list.add(rangedBoard);
        list.add(meleeBoard);
        list.add(specialBoard);
        list.add(deck);
        list.add(graveyard);
        list.add(voidd);
        return list;
    }

    public static List<Card> newCardlist(List<Card> list) {
        List<Card> newList = new ArrayList<>();
        for (Card card:list) {
            newList.add(card.copy());
        }
        return newList;
    }

    public HalveBoardState getOther(Boardstate board) {
        if (board.own == this) {
            return board.enemy;
        } else {
            return board.own;
        }
    }

    public void drawCard() {
        if (this.deck.size() < 1){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("no Deck"));
        } else {
            hand.add(this.deck.pop());
        }
    }

    public void drawCard(int x) {
        for (int i=0; i<x ; i++){
            drawCard();
        }
    }

    public void drawCard(Card card) {
        deck.remove(card);
        hand.add(card);
        Collections.shuffle(deck);
    }

    public int getStrength () {
        return Card.getStrengthFromList(this.meleeBoard) + Card.getStrengthFromList(this.rangedBoard) + Card.getStrengthFromList(this.specialBoard);
    }
    public boolean getIsYourTurn() {
        return this.isYourTurn;
    }
    public void setYourTurn(boolean b) {
        this.isYourTurn = b;
    }


    public List<Card> getAllCardsOnBoard() {
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(this.meleeBoard);
        cards.addAll(this.rangedBoard);
        cards.addAll(this.specialBoard);
        return cards;
    }

}
