package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.core.init.gui.events.CardDamagedEvent;
import at.plaus.minecardmod.core.init.gui.events.CardSelectedEvent;
import at.plaus.minecardmod.core.init.gui.events.EtbEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BothBordstates {
    public MinecardBoardState own;
    public MinecardBoardState enemy;
    public boolean gamePaused = false;
    public List<CardSelectedEvent> selectionListeners = new ArrayList<CardSelectedEvent>();
    public List<CardDamagedEvent> damageListeners = new ArrayList<CardDamagedEvent>();
    public List<EtbEvent> etbListeners = new ArrayList<EtbEvent>();
    public List<List<MinecardCard>> selectionTargets = new ArrayList<List<MinecardCard>>();

    public enum Player {
        OWN,
        ENEMY
    }


    public MinecardBoardState getBoardState(Player p) {
        if (p == Player.OWN) {
            return own;
        } else {
            return enemy;
        }
    }

    public BothBordstates getReverse(){
        return new BothBordstates(enemy, own);
    }

    public BothBordstates(MinecardBoardState own, MinecardBoardState enemy) {
        this.enemy = enemy;
        this.own = own;
    }

    public BothBordstates(BothBordstates board) {
        this.enemy = new MinecardBoardState(board.enemy);
        this.own = new MinecardBoardState(board.own);
        this.gamePaused = board.gamePaused;
        this.selectionListeners = new ArrayList<>(board.selectionListeners);
        this.damageListeners = new ArrayList<>(board.damageListeners);
        this.etbListeners = new ArrayList<>(board.etbListeners);
        this.selectionTargets = new ArrayList<>(board.selectionTargets);
    }

    public BothBordstates playCardFromHand(int i, Player player) {
        MinecardCard card = this.getBoardState(player).hand.get(i);
        this.getBoardState(player).hand.remove(i);
        return playCard(card, player);
    }

    public BothBordstates playCard(MinecardCard card, Player player) {
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
        }
        BothBordstates tempBoard = this;List<EtbEvent> tempListener = new ArrayList<>();
        tempListener.addAll(etbListeners);
        tempBoard = card.etb(tempBoard);

        for (EtbEvent event:tempListener) {
            tempBoard = event.onEtb(card, tempBoard);
        }

        return tempBoard;
    }

    public BothBordstates clearBoard() {
        BothBordstates newBoard = new BothBordstates(this);

        for (MinecardCard card: own.meleeBoard) {
            newBoard.own.meleeBoard.remove(card);
        }
        for (MinecardCard card: own.rangedBoard) {
            newBoard.own.rangedBoard.remove(card);
        }
        for (MinecardCard card: own.specialBoard) {
            newBoard.own.specialBoard.remove(card);
        }
        for (MinecardCard card: enemy.meleeBoard) {
            newBoard.enemy.meleeBoard.remove(card);
        }
        for (MinecardCard card: enemy.rangedBoard) {
            newBoard.enemy.rangedBoard.remove(card);
        }
        for (MinecardCard card: enemy.specialBoard) {
            newBoard.enemy.specialBoard.remove(card);
        }


        for (MinecardCard card: own.meleeBoard) {
            newBoard = card.die(newBoard);
        }
        for (MinecardCard card: own.rangedBoard) {
            newBoard = card.die(newBoard);
        }
        for (MinecardCard card: own.specialBoard) {
            newBoard = card.die(newBoard);
        }
        for (MinecardCard card: enemy.meleeBoard) {
            newBoard = card.die(newBoard);
        }
        for (MinecardCard card: enemy.rangedBoard) {
            newBoard = card.die(newBoard);
        }
        for (MinecardCard card: enemy.specialBoard) {
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


    public List<List<MinecardCard>> getListOfCardLists () {
        List<List<MinecardCard>> list = new ArrayList<>();
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
