package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Boardstate {
    public HalveBoardState own;
    public HalveBoardState enemy;
    public boolean gamePaused = false;
    public List<CardSelectedEvent> selectionListeners = new ArrayList<CardSelectedEvent>();
    public List<CardDamagedEvent> damageListeners = new ArrayList<CardDamagedEvent>();
    public List<EtbEvent> etbListeners = new ArrayList<EtbEvent>();
    public List<List<Card>> selectionTargets = new ArrayList<List<Card>>();

    public enum Player {
        OWN,
        ENEMY
    }


    public HalveBoardState getBoardState(Player p) {
        if (p == Player.OWN) {
            return own;
        } else {
            return enemy;
        }
    }

    public Boardstate getReverse(){
        return new Boardstate(enemy, own);
    }

    public Boardstate(HalveBoardState own, HalveBoardState enemy) {
        this.enemy = enemy;
        this.own = own;
    }

    public Boardstate(Boardstate board) {
        this.enemy = new HalveBoardState(board.enemy);
        this.own = new HalveBoardState(board.own);
        this.gamePaused = board.gamePaused;
        this.selectionListeners = new ArrayList<>(board.selectionListeners);
        this.damageListeners = new ArrayList<>(board.damageListeners);
        this.etbListeners = new ArrayList<>(board.etbListeners);
        this.selectionTargets = new ArrayList<>(board.selectionTargets);
    }

    public Boardstate playCardFromHand(int i, Player player) {
        Card card = this.getBoardState(player).hand.get(i);
        this.getBoardState(player).hand.remove(i);
        return playCard(card, player);
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
        Boardstate tempBoard = this;List<EtbEvent> tempListener = new ArrayList<>();
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
            newBoard.own.meleeBoard.remove(card);
        }
        for (Card card: own.rangedBoard) {
            newBoard.own.rangedBoard.remove(card);
        }
        for (Card card: own.specialBoard) {
            newBoard.own.specialBoard.remove(card);
        }
        for (Card card: enemy.meleeBoard) {
            newBoard.enemy.meleeBoard.remove(card);
        }
        for (Card card: enemy.rangedBoard) {
            newBoard.enemy.rangedBoard.remove(card);
        }
        for (Card card: enemy.specialBoard) {
            newBoard.enemy.specialBoard.remove(card);
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

    public void switchTurn() {
        own.isYourTurn = !own.isYourTurn;
        enemy.isYourTurn = !enemy.isYourTurn;
        if (enemy.hand.isEmpty()) {
            enemy.hasPassed = true;
        }
        if (own.hand.isEmpty()) {
            own.hasPassed = true;
        }
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



}
