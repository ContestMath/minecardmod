package at.plaus.minecardmod.core.init.CardGame.Ai;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimpleAi extends CardAi{

    @Override
    public Boardstate boardTransformation(Boardstate board) {
        Boardstate newBoard = board;
        if (board.selectionStack.isEmpty()) {
            if ((board.own.hasPassed && board.own.getStrength() < board.enemy.getStrength()) || (board.own.getStrength()-7>board.enemy.getStrength() && board.enemy.lifePoints>1) || (board.enemy.getStrength()-7>board.own.getStrength())) {
                board.enemy.hasPassed = true;
                return board;
            }

            newBoard = board.playCardFromHand(newBoard.enemy.hand.get(ThreadLocalRandom.current().nextInt(0, newBoard.enemy.hand.size())));
            return newBoard;
        } else {
            List<Card> targets = newBoard.selectionStack.peek().b.onFindTargets(newBoard.selectionStack.peek().c, newBoard);
            int cardIndex = selectionTarget(newBoard);
            newBoard = targets.get(cardIndex).selected(newBoard);
            return newBoard;
        }
    }

    public int selectionTarget(Boardstate board) {
        List<Card> targets = board.selectionStack.peek().b.onFindTargets(board.selectionStack.peek().c, board);
        List<Boardstate> list = new ArrayList<>();
        Boardstate newBoard = new Boardstate(board);
        for (int i = 0;i<targets.size();i++) {
            list.add(targets.get(i).selected(newBoard));
            newBoard = new Boardstate(board);
        }
        int result = 0;
        for (int i = 1;i<targets.size();i++) {
            if (list.get(i).enemy.getStrength() - list.get(i).own.getStrength() > list.get(result).enemy.getStrength() - list.get(result).own.getStrength()) {
                result = i;
            }
        }
        return result;
    }
}
