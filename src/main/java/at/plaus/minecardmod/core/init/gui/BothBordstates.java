package at.plaus.minecardmod.core.init.gui;

import java.util.Objects;

public class BothBordstates {
    public MinecardBoardState own;
    public MinecardBoardState enemy;
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
        return card.ETB(this);
    }

}
