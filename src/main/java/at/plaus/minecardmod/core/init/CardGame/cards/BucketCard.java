package at.plaus.minecardmod.core.init.CardGame.cards;

import at.plaus.minecardmod.core.init.CardGame.*;

public class BucketCard extends Card {
    public BucketCard() {
        super(
                0,
                "textures/gui/bucket_card.png",
                CardTypes.EFFECT,
                new String[]{"tooltip.minecardmod.cards.bucket"},
                "Bucket");
        emeraldCost = 1;
    }
    Card waterOption = new WaterOption();
    Card lavaOption = new LavaOption();
    Card milkOption = new MilkOption();
    @Override
    public Boardstate etb(Boardstate board) {
        Boardstate tempBoard = board;
        HalveBoardState halveBoardState = getOwedHalveBoard(board);
        halveBoardState.option_selection.add(lavaOption);
        halveBoardState.option_selection.add(waterOption);
        halveBoardState.option_selection.add(milkOption);

        tempBoard.addSelectionEvent((source, card, boardstate) -> {
            if (card.equals(waterOption)) {
                boardstate.addSelectionEvent(
                        (sourceInner, selected, b) -> {
                            b = selected.voidd(b);
                            return b.appearCard(selected.getNew(), selected.getOwedHalveBoard(b));
                        },
                        getTargets(),
                        this
                );
            }
            if (card.equals(lavaOption)) {
                boardstate.addSelectionEvent(
                        (sourceInner, selected, b) -> selected.damage(5, b, sourceInner),
                        getTargets(),
                        this
                );
            }
            if (card.equals(milkOption)) {
                for (Card cardx:boardstate.getAllCardsOnBoard()) {
                    cardx.isOnFire = false;
                }
            }
            boardstate.clearOptions();
            return boardstate;
        }, getOptionTargets(), this);
        return tempBoard;
    }

     public static class WaterOption extends OptionsCard {
         public WaterOption() {
             super("textures/gui/bucket_card.png", new String[]{"tooltip.minecardmod.cards.bucket1"}, "Water");
         }
     }
    public static class LavaOption extends OptionsCard {
        public LavaOption() {
            super("textures/gui/bucket_card.png", new String[]{"tooltip.minecardmod.cards.bucket2"}, "Lava");
        }
    }
    public static class MilkOption extends OptionsCard {
        public MilkOption() {
            super("textures/gui/bucket_card.png", new String[]{"tooltip.minecardmod.cards.bucket3"}, "Milk");
        }
    }
}
