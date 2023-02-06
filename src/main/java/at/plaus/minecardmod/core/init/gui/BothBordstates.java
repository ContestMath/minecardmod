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

    public MinecardBoardState getOpponentBoardState(Player p) {
        if (p == Player.OWN) {
            return enemy;
        } else {
            return own;
        }
    }

    public BothBordstates(MinecardBoardState own, MinecardBoardState enemy) {
        this.enemy = enemy;
        this.own = own;
    }

    public BothBordstates playCardFromHand(int i, Player player) {
        MinecardCard card = this.getBoardState(player).hand.get(i);
        this.getBoardState(player).hand.remove(i);
        playCard(card, player);
        return card.ETB(this);
    }

    public BothBordstates playCard(MinecardCard card, Player player) {
        MinecardBoardState b = this.getBoardState(player);
        if (!card.isSpy) {
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                b.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                b.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                b.specialBoard.add(card);
            }
        } else {
            b = this.getBoardState(player);
            if (Objects.equals(card.type, CardTypes.MELEE)) {
                b.meleeBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.RANGED)) {
                b.rangedBoard.add(card);
            }
            if (Objects.equals(card.type, CardTypes.SPECIAL)) {
                b.specialBoard.add(card);
            }
        }
        return card.ETB(this);
    }

}
