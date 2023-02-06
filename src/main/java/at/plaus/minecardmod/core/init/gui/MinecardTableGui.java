package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.GlobalValues;
import at.plaus.minecardmod.core.init.MinecardRules;
import at.plaus.minecardmod.core.init.gui.cards.BlueMinecardCard;
import at.plaus.minecardmod.core.init.gui.cards.BrownMinecardCard;
import at.plaus.minecardmod.core.init.gui.cards.YellowMinecardCard;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MinecardTableGui extends AbstractMinecardScreen {

    private final ResourceLocation GUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui.png");

    
    public BothBordstates board = new BothBordstates(new MinecardBoardState(), new MinecardBoardState());
    public static final ITextComponent title = new TranslationTextComponent("tooltip.minecardmod.minecard_table");

    int index = 0;
    public int numberStringWidth = 6;
    //int offsetX;
    //int offsetY;
    boolean gameHasEnded = false;

    public MinecardTableGui() {
        super(new TranslationTextComponent("tooltip.minecardmod.minecard_table"));
    }

    public int[] getCardPosInList(int i, List<MinecardCard> list) {
        int x = offsetX + MinecardTableImageLocations.guiwidth/2 - 5 * (list.size()-1) + 10*i + MinecardCard.cardwidth*i - MinecardCard.cardwidth/2*list.size();
        int yStart = offsetY+ MinecardTableImageLocations.guiheight- MinecardCard.cardheight;
        int space = 5;
        if (list.equals(board.own.hand)) {
            return new int[]{x-15, yStart-10};
        } else if (list.equals(board.own.specialBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight-3*space};
        } else if (list.equals(board.own.rangedBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*2-4*space};
        } else if (list.equals(board.own.meleeBoard)) {
            return new int[]{x, yStart - MinecardCard.cardheight *3-5*space};
        } else if (list.equals(board.enemy.meleeBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*4-6*space};
        } else if (list.equals(board.enemy.rangedBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*5-7*space};
        } else if (list.equals(board.enemy.specialBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*6-8*space};
        }
        else return new int[]{0, 0};
    }

    @Override
    public void startup() {
        PlayerEntity p = Minecraft.getInstance().player;

        if (GlobalValues.savedBoardTemp.containsKey(p)) {
            loadGame(GlobalValues.savedBoardTemp.get(p));
        } else {
            if (GlobalValues.deck1.containsKey(p)) {
                List<MinecardCard> d = GlobalValues.deck1.get(p);
                Collections.shuffle(d, new Random());
                board.own.deck.addAll(d);
            } else {
                board.enemy.isYourTurn = false;
                for (int i = 0; i < 10; i++) {
                    board.own.deck.push(new BlueMinecardCard());
                    board.own.deck.push(new YellowMinecardCard());
                    board.own.deck.push(new BrownMinecardCard());
                }
            }
            for (int i = 0; i < 10; i++) {
                board.enemy.deck.push(new BlueMinecardCard());
                board.enemy.deck.push(new YellowMinecardCard());
                board.enemy.deck.push(new BrownMinecardCard());
            }

            board.own.drawCard(MinecardRules.startingHandsize);
            board.enemy.drawCard(MinecardRules.startingHandsize);
        }
        super.startup();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderBackground(matrixStack); //black Background
        this.renderWindow(matrixStack, offsetX, offsetY);
        renderOtherStuff(matrixStack);
        renderCards(matrixStack);
        renderHighlight(matrixStack, mouseX, mouseY);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty("Minecard table"), offsetX+2, offsetY+2, -1);
        renderValues(matrixStack, offsetX, offsetY);
        renderAllTooltipp(matrixStack, mouseX, mouseY);
    }

    @Override
    public void tick() {
        if (board.enemy.hasPassed) {
            board.enemy.isYourTurn = false;
        }
        enemyPlay();
        if (board.own.hasPassed) {
            board.own.isYourTurn = false;
            board.enemy.isYourTurn = true;
        }
        if (board.own.lifePoints == 0 || board.enemy.lifePoints == 0) {
            endGame();
        }
        if (board.own.hasPassed && board.enemy.hasPassed) {
            endRound();
        }
    }

    private void renderHighlight(MatrixStack matrixStack, int mouseX, int mouseY) {

        renderCardHighlightFromList(matrixStack, mouseX, mouseY, board.own.hand);

        if (isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
            fillGradient(matrixStack, offsetX+20, offsetY+ MinecardTableImageLocations.guiheight-25-20, offsetX+20+40, offsetY+ MinecardTableImageLocations.guiheight-25-20+25, -1072689136, -804253680);
        }
    }

    private void renderAllTooltipp(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (isWithinBoundingBox(mouseX, mouseY,offsetX + MinecardTableImageLocations.optionsX, offsetX +MinecardTableImageLocations.optionsX+MinecardTableImageLocations.optionsSide, offsetY + MinecardTableImageLocations.optionsY,  offsetY +MinecardTableImageLocations.optionsY+MinecardTableImageLocations.optionsSide)) {
            //renderTooltip(matrixStack, new StringTextComponent("hi"), mouseX, mouseY);
            renderTooltip(matrixStack, new TranslationTextComponent("tooltip.gui.options"), mouseX, mouseY);
        }
        renderCardTooltip(matrixStack, board.own.hand, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.own.meleeBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.own.rangedBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.own.specialBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.enemy.meleeBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.enemy.rangedBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, board.enemy.specialBoard, mouseX, mouseY);
    }

    public void renderCards (MatrixStack matrixStack) {
        renderCardsFromList(matrixStack, board.own.hand);
        renderCardsFromList(matrixStack, board.own.meleeBoard);
        renderCardsFromList(matrixStack, board.own.rangedBoard);
        renderCardsFromList(matrixStack, board.enemy.meleeBoard);
        renderCardsFromList(matrixStack, board.enemy.rangedBoard);
        renderCardsFromList(matrixStack, board.enemy.specialBoard);
        renderCardsFromList(matrixStack, board.own.specialBoard);
    }
    public void renderOtherStuff(MatrixStack matrixStack) {
        this.minecraft.getTextureManager().bind(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/pass.png"));
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.PassX, offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY, 0, 0, MinecardTableImageLocations.PassWidth, MinecardTableImageLocations.PassHeight);
        this.minecraft.getTextureManager().bind(new ResourceLocation(""));
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.guiwidth- MinecardCard.cardwidth-10, offsetY+ MinecardTableImageLocations.guiheight- MinecardCard.cardheight-10, 0, 0, MinecardCard.cardwidth, MinecardCard.cardheight);
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.guiwidth- MinecardCard.cardwidth-10, offsetY+10, 0, 0, MinecardCard.cardwidth, MinecardCard.cardheight);
        this.minecraft.getTextureManager().bind(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/heart.png"));
        if (board.own.lifePoints == 2) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (board.own.lifePoints == 1) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        if (board.enemy.lifePoints == 2) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (board.enemy.lifePoints == 1) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        this.minecraft.getTextureManager().bind(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/grey.png"));
        this.blit(matrixStack, offsetX+MinecardTableImageLocations.optionsX, offsetY+MinecardTableImageLocations.optionsY, 0, 0, MinecardTableImageLocations.optionsSide, MinecardTableImageLocations.optionsSide);
    }

    public void renderWindow(MatrixStack matrixStack, int offsetX, int offsetY) {
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, offsetX, offsetY, 0, 0, MinecardTableImageLocations.guiwidth, MinecardTableImageLocations.guiheight);
    }

    public void renderValues(MatrixStack matrixStack, int offsetX, int offsetY) {
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(board.own.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.deck.size()).length())-16, offsetY+ MinecardTableImageLocations.guiheight-18, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(board.enemy.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.deck.size()).length())-16, offsetY+ MinecardCard.cardheight+2, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(board.enemy.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.enemy.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2-20, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(board.own.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2+20, -1);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            onCloseOrSwitch();
            Minecraft.getInstance().setScreen(new DeckBuilderGui());
            return true;
        } else if (keyCode == 81) {
            board.own.drawCard();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cardindex = getTouchingCardFromList(mouseX, mouseY, board.own.hand);
        if (button == 0) { //left mouse
            if (board.own.isYourTurn) {
                if (cardindex != -1){//board.PlayCardFromHand(cardindex, BothBordstates.Player.OWN)) {
                    board = board.playCardFromHand(cardindex, BothBordstates.Player.OWN);
                    board.own.isYourTurn=false;
                    board.enemy.isYourTurn=true;
                }
                if (board.own.hand.size() == 0) {
                    board.own.hasPassed = true;
                }
            }
            if (isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
                board.own.hasPassed = true;
            }
        }
        /*
        if (button == 1) { // right mouse
        }
         */
        return true;
    }

    public void enemyPlay() {
        if (board.enemy.hand.size() == 0) {
            board.enemy.hasPassed = true;
            board.enemy.isYourTurn = false;
        }
        if (board.enemy.isYourTurn) {
            board = board.playCardFromHand(ThreadLocalRandom.current().nextInt(0, board.enemy.hand.size()), BothBordstates.Player.ENEMY);
        }
        board.enemy.isYourTurn = false;
        board.own.isYourTurn = true;
    }
    public void endRound() {
        if (board.own.getStrength() > board.enemy.getStrength()) {
            board.enemy.lifePoints --;
        }
        if (board.own.getStrength() < board.enemy.getStrength()) {
            board.own.lifePoints --;
        }
        if (board.own.getStrength() == board.enemy.getStrength()) {
            board.own.lifePoints --;
            board.enemy.lifePoints --;
        }
        board.enemy.hasPassed = false;
        board.own.hasPassed = false;
        board.enemy.clearBoard();
        board.own.clearBoard();
        board.enemy.drawCard(2);
        board.own.drawCard(2);
    }
    public void endGame() {
        gameHasEnded = true;
        if (board.enemy.lifePoints == 0 && board.own.lifePoints == 0) {
            Minecraft.getInstance().player.chat("It is a Draw!");
        } else if(board.enemy.lifePoints == 0) {
            Minecraft.getInstance().player.chat("You have won!");
        } else if(board.own.lifePoints == 0) {
            Minecraft.getInstance().player.chat("You Have Lost!");
        }
        onClose();
    }

    public void loadGame(MinecardBoardState[] boardstate) {
        board.own = boardstate[0];
        board.enemy = boardstate[1];
    }

    public void onCloseOrSwitch() {
        if (!gameHasEnded) {
            GlobalValues.savedBoardTemp.put(Minecraft.getInstance().player, new MinecardBoardState[]{board.own, board.enemy});
        } else {
            GlobalValues.savedBoardTemp.remove(Minecraft.getInstance().player);
        }
    }

    @Override
    public void onClose() {
        onCloseOrSwitch();
        super.onClose();
    }

}
