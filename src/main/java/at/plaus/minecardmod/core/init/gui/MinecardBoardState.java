package at.plaus.minecardmod.core.init.gui;

import net.minecraft.client.Minecraft;

import java.util.*;

public class MinecardBoardState {
    public Stack<at.plaus.minecardmod.core.init.gui.MinecardCard> deck;
    public List<MinecardCard> hand;
    public List<MinecardCard> meleeBoard;
    public List<MinecardCard> rangedBoard;
    public List<MinecardCard> specialBoard;
    public boolean isYourTurn = true;
    public boolean hasPassed = false;
    public int lifePoints = 2;


    public MinecardBoardState() {
        this.deck = new Stack<MinecardCard>();
        this.hand = new ArrayList<MinecardCard>();
        this.meleeBoard = new ArrayList<MinecardCard>();
        this.rangedBoard = new ArrayList<MinecardCard>();
        this.specialBoard = new ArrayList<MinecardCard>();
    }

    public MinecardBoardState(MinecardBoardState boardState) {
        this.isYourTurn = boardState.isYourTurn;
        this.hasPassed = boardState.hasPassed;
        this.lifePoints = boardState.lifePoints;
        Stack<MinecardCard> newDeck = new Stack<>();
        newDeck.addAll(boardState.deck);
        this.deck = newDeck;
        this.hand = new ArrayList<MinecardCard>(boardState.hand);
        this.meleeBoard = new ArrayList<MinecardCard>(boardState.meleeBoard);
        this.rangedBoard = new ArrayList<MinecardCard>(boardState.rangedBoard);
        this.specialBoard = new ArrayList<MinecardCard>(boardState.specialBoard);
    }

    public void drawCard() {
        if (this.deck.size() < 1){
            Minecraft.getInstance().player.chat("no deck");
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
        return MinecardCard.getStrengthFromList(this.meleeBoard) + MinecardCard.getStrengthFromList(this.rangedBoard) + MinecardCard.getStrengthFromList(this.specialBoard);
    }
    public boolean getIsYourTurn() {
        return this.isYourTurn;
    }
    public void setYourTurn(boolean b) {
        this.isYourTurn = b;
    }


    public List<MinecardCard> getAllCards() {
        List<MinecardCard> cards = new ArrayList<MinecardCard>();
        cards.addAll(this.deck);
        cards.addAll(this.hand);
        cards.addAll(this.meleeBoard);
        cards.addAll(this.rangedBoard);
        cards.addAll(this.specialBoard);
        return cards;
    }
}
