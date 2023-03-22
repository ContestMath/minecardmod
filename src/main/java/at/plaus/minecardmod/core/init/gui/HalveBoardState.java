package at.plaus.minecardmod.core.init.gui;

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
    public boolean isYourTurn = true;
    public boolean hasPassed = false;
    public int lifePoints = 2;
    public int emeraldCount = 0;
    public int cthulhuCounter = 0;


    public HalveBoardState() {
        this.deck = new Stack<Card>();
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
        this.hand = new ArrayList<Card>(boardState.hand);
        this.meleeBoard = new ArrayList<Card>(boardState.meleeBoard);
        this.rangedBoard = new ArrayList<Card>(boardState.rangedBoard);
        this.specialBoard = new ArrayList<Card>(boardState.specialBoard);
        this.graveyard = new ArrayList<Card>(boardState.graveyard);
        this.emeraldCount = boardState.emeraldCount;
        this.cthulhuCounter = boardState.cthulhuCounter;
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

    public int getStrength () {
        return Card.getStrengthFromList(this.meleeBoard) + Card.getStrengthFromList(this.rangedBoard) + Card.getStrengthFromList(this.specialBoard);
    }
    public boolean getIsYourTurn() {
        return this.isYourTurn;
    }
    public void setYourTurn(boolean b) {
        this.isYourTurn = b;
    }


    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(this.deck);
        cards.addAll(this.hand);
        cards.addAll(this.meleeBoard);
        cards.addAll(this.rangedBoard);
        cards.addAll(this.specialBoard);
        cards.addAll(this.graveyard);
        return cards;
    }

    public List<Card> getAllCardsOnBoard() {
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(this.meleeBoard);
        cards.addAll(this.rangedBoard);
        cards.addAll(this.specialBoard);
        return cards;
    }
}
