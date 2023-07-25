package at.plaus.minecardmod.core.init.CardGame.Ai;

import at.plaus.minecardmod.core.init.CardGame.Boardstate;
import at.plaus.minecardmod.core.init.CardGame.Card;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAi extends CardAi{

    @Override
    public Boardstate boardTransformation(Boardstate board) {
        Boardstate newBoard = board;
        if (board.selectionStack.isEmpty()) {
            newBoard = board.playCardFromHand(board.enemy.hand.get(ThreadLocalRandom.current().nextInt(0, board.enemy.hand.size())));
            if (newBoard.enemy.hand.isEmpty()) {
                newBoard.enemy.hasPassed = true;
            }
            return newBoard;
        } else {
            List<Card> targets = newBoard.selectionStack.peek().b.onFindTargets(board.selectionStack.peek().c, newBoard);
            if (targets.size() != 0) {
                newBoard = targets.get(ThreadLocalRandom.current().nextInt(0, targets.size())).selected(newBoard);
            }
            return newBoard;
        }
    }
}
