package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;
import at.plaus.minecardmod.core.init.gui.events.SymbolSelectedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Boardstate {
    public HalveBoardState own;
    public HalveBoardState enemy;
    public boolean gamePaused = false;
    public Stack<CardSelectedEvent> selectionCardListeners = new Stack<>();
    public Stack<SymbolSelectedEvent> selectionSymbolListeners = new Stack<>();
    public Card selectionSource;
    public List<CardDamagedEvent> damageListeners = new ArrayList<CardDamagedEvent>();
    public List<EtbEvent> etbListeners = new ArrayList<EtbEvent>();
    public List<List<Card>> selectionCardTargets = new ArrayList<List<Card>>();
    public List<CardMechanicSymbol> selectionSymbolTargets = new ArrayList<>();

    public enum Player {
        OWN,
        ENEMY
    }


    @Deprecated
    public HalveBoardState getBoardState(Player p) {
        if (p == Player.OWN) {
            return own;
        } else {
            return enemy;
        }
    }

    public Player getPlayerFromHalveBoard(HalveBoardState halveBoardState) {
        if (halveBoardState.equals(enemy)) {
            return Player.ENEMY;
        }
        return Player.OWN;
    }

    public Boardstate getReverse(){
        return new Boardstate(enemy, own);
    }

    @Deprecated
    public Boardstate(HalveBoardState own, HalveBoardState enemy) {
        this.enemy = enemy;
        this.own = own;
    }

    public Boardstate() {
        this.enemy = new HalveBoardState();
        this.own = new HalveBoardState();
    }

    public Boardstate(Boardstate board) {
        this.enemy = new HalveBoardState(board.enemy);
        this.own = new HalveBoardState(board.own);
        this.gamePaused = board.gamePaused;
        this.selectionCardListeners = new Stack<>();
        this.selectionCardListeners.addAll(board.selectionCardListeners);
        this.damageListeners = new ArrayList<>(board.damageListeners);
        this.etbListeners = new ArrayList<>(board.etbListeners);
        this.selectionCardTargets = new ArrayList<>(board.selectionCardTargets);
        this.selectionSource = board.selectionSource;
    }

    public Boardstate playCardFromHand(int i, Player player) {
        Card card = this.getBoardState(player).hand.get(i);
        if (card.isPlayable(this)) {
            this.getBoardState(player).emeraldCount -= card.emeraldCost;
            this.getBoardState(player).hand.remove(i);
            MinecardTableGui.cardWasPlayed = true;
            return playCard(card, player);
        }
        MinecardTableGui.cardWasPlayed = false;
        return this;
    }

    public Boardstate playCard(Card card, Player player) {
        boolean isOwnBoard = true;
        if (player == Player.ENEMY) {
            isOwnBoard = !isOwnBoard;
        }
        if (card.isSpy) {
            isOwnBoard = !isOwnBoard;
        }

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
                this.own.graveyard.add(card);
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
                this.enemy.graveyard.add(card);
            }
        }
        Boardstate tempBoard = this;
        List<EtbEvent> tempListener = new ArrayList<>();
        tempListener.addAll(etbListeners);
        tempBoard = card.etb(tempBoard);

        for (EtbEvent event:tempListener) {
            tempBoard = event.onEtb(card, tempBoard);
        }

        return tempBoard;
    }

    public Boardstate clearBoard() {
        Boardstate newBoard = new Boardstate(this);

        for (Card card: own.meleeBoard) {
            newBoard.own.graveyard.add(card);
            newBoard.own.meleeBoard.remove(card);
        }
        for (Card card: own.rangedBoard) {
            newBoard.own.rangedBoard.remove(card);
            newBoard.own.graveyard.add(card);
        }
        for (Card card: own.specialBoard) {
            newBoard.own.specialBoard.remove(card);
            newBoard.own.graveyard.add(card);
        }
        for (Card card: enemy.meleeBoard) {
            newBoard.enemy.meleeBoard.remove(card);
            newBoard.own.graveyard.add(card);
        }
        for (Card card: enemy.rangedBoard) {
            newBoard.enemy.rangedBoard.remove(card);
            newBoard.own.graveyard.add(card);
        }
        for (Card card: enemy.specialBoard) {
            newBoard.enemy.specialBoard.remove(card);
            newBoard.own.graveyard.add(card);
        }


        for (Card card: own.meleeBoard) {
            newBoard = card.die(newBoard);
        }
        for (Card card: own.rangedBoard) {
            newBoard = card.die(newBoard);
        }
        for (Card card: own.specialBoard) {
            newBoard = card.die(newBoard);
        }
        for (Card card: enemy.meleeBoard) {
            newBoard = card.die(newBoard);
        }
        for (Card card: enemy.rangedBoard) {
            newBoard = card.die(newBoard);
        }
        for (Card card: enemy.specialBoard) {
            newBoard = card.die(newBoard);
        }
        return newBoard;

    }

    public Boardstate switchTurn() {
        Boardstate tempBoard = new Boardstate(this);
        tempBoard.own.isYourTurn = !tempBoard.own.isYourTurn;
        tempBoard.enemy.isYourTurn = !tempBoard.enemy.isYourTurn;
        for (Card card:getAllCardsOnBoard()) {
            if (card.getOwedHalveBoard(tempBoard).isYourTurn) {
                tempBoard = card.atTheStartOfTurn(tempBoard);
            }
        }
        return tempBoard;
    }


    public List<List<Card>> getListOfCardLists () {
        List<List<Card>> list = new ArrayList<>();
        list.add(own.hand);
        list.add(own.rangedBoard);
        list.add(own.meleeBoard);
        list.add(own.specialBoard);
        list.add(enemy.hand);
        list.add(enemy.meleeBoard);
        list.add(enemy.rangedBoard);
        list.add(enemy.specialBoard);
        return list;
    }

    public List<Card> getAllCardsOnBoard() {
        List<Card> list = new ArrayList<>();
        list.addAll(own.getAllCardsOnBoard());
        list.addAll(enemy.getAllCardsOnBoard());
        return list;
    }


}
