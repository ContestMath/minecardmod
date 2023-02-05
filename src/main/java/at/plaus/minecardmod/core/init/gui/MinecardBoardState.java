package at.plaus.minecardmod.core.init.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

public class MinecardBoardState {
    public Stack<at.plaus.minecardmod.core.init.gui.MinecardCard> deck;
    public final List<MinecardCard> hand;
    public final List<MinecardCard> meleeBoard;
    public final List<MinecardCard> rangedBoard;
    public final List<MinecardCard> specialBoard;
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

    public boolean playCard(int i) {
        if (i != -1) {
            if (Objects.equals(hand.get(i).type, "Melee")) {
                meleeBoard.add(hand.get(i));
            }
            if (Objects.equals(hand.get(i).type, "Ranged")) {
                rangedBoard.add(hand.get(i));
            }
            if (Objects.equals(hand.get(i).type, "Special")) {
                specialBoard.add(hand.get(i));
            }
            hand.remove(i);
            return true;
        }
        return false;
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

    public void clearBoard() {
        //List<minecardCard> wholeBoard = new ArrayList<minecardCard>();
        //wholeBoard.addAll(meleeBoard);
        //wholeBoard.addAll(rangedBoard);
        //wholeBoard.addAll(specialBoard);
        Iterator<MinecardCard> iMelee = meleeBoard.iterator();
        Iterator<MinecardCard> iRanged = rangedBoard.iterator();
        Iterator<MinecardCard> iSpecial = specialBoard.iterator();
        while (iMelee.hasNext()) {
            MinecardCard card = iMelee.next();
            iMelee.remove();
        }
        while (iRanged.hasNext()) {
            MinecardCard card = iRanged.next();
            iRanged.remove();
        }
        while (iSpecial.hasNext()) {
            MinecardCard card = iSpecial.next();
            iSpecial.remove();
        }
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
