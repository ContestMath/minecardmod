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


    public MinecardBoardState ownBoard = new MinecardBoardState();
    public MinecardBoardState enemyBoard = new MinecardBoardState();
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
        if (list.equals(ownBoard.hand)) {
            return new int[]{x-15, yStart-10};
        } else if (list.equals(ownBoard.specialBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight-3*space};
        } else if (list.equals(ownBoard.rangedBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*2-4*space};
        } else if (list.equals(ownBoard.meleeBoard)) {
            return new int[]{x, yStart - MinecardCard.cardheight *3-5*space};
        } else if (list.equals(enemyBoard.meleeBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*4-6*space};
        } else if (list.equals(enemyBoard.rangedBoard)) {
            return new int[]{x, yStart- MinecardCard.cardheight*5-7*space};
        } else if (list.equals(enemyBoard.specialBoard)) {
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
                ownBoard.deck.addAll(d);
            } else {
                enemyBoard.isYourTurn = false;
                for (int i = 0; i < 10; i++) {
                    ownBoard.deck.push(new BlueMinecardCard());
                    ownBoard.deck.push(new YellowMinecardCard());
                    ownBoard.deck.push(new BrownMinecardCard());
                }
            }
            for (int i = 0; i < 10; i++) {
                enemyBoard.deck.push(new BlueMinecardCard());
                enemyBoard.deck.push(new YellowMinecardCard());
                enemyBoard.deck.push(new BrownMinecardCard());
            }

            ownBoard.drawCard(MinecardRules.startingHandsize);
            enemyBoard.drawCard(MinecardRules.startingHandsize);
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
        if (enemyBoard.hasPassed) {
            enemyBoard.isYourTurn = false;
        }
        enemyPlay();
        if (ownBoard.hasPassed) {
            ownBoard.isYourTurn = false;
            enemyBoard.isYourTurn = true;
        }
        if (ownBoard.lifePoints == 0 || enemyBoard.lifePoints == 0) {
            endGame();
        }
        if (ownBoard.hasPassed && enemyBoard.hasPassed) {
            endRound();
        }
    }

    private void renderHighlight(MatrixStack matrixStack, int mouseX, int mouseY) {

        renderCardHighlightFromList(matrixStack, mouseX, mouseY, ownBoard.hand);

        if (isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
            fillGradient(matrixStack, offsetX+20, offsetY+ MinecardTableImageLocations.guiheight-25-20, offsetX+20+40, offsetY+ MinecardTableImageLocations.guiheight-25-20+25, -1072689136, -804253680);
        }
    }

    private void renderAllTooltipp(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (isWithinBoundingBox(mouseX, mouseY,offsetX + MinecardTableImageLocations.optionsX, offsetX +MinecardTableImageLocations.optionsX+MinecardTableImageLocations.optionsSide, offsetY + MinecardTableImageLocations.optionsY,  offsetY +MinecardTableImageLocations.optionsY+MinecardTableImageLocations.optionsSide)) {
            //renderTooltip(matrixStack, new StringTextComponent("hi"), mouseX, mouseY);
            renderTooltip(matrixStack, new TranslationTextComponent("tooltip.gui.options"), mouseX, mouseY);
        }
        renderCardTooltip(matrixStack, ownBoard.hand, mouseX, mouseY);
        renderCardTooltip(matrixStack, ownBoard.meleeBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, ownBoard.rangedBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, ownBoard.specialBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, enemyBoard.meleeBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, enemyBoard.rangedBoard, mouseX, mouseY);
        renderCardTooltip(matrixStack, enemyBoard.specialBoard, mouseX, mouseY);
    }

    public void renderCards (MatrixStack matrixStack) {
        renderCardsFromList(matrixStack, ownBoard.hand);
        renderCardsFromList(matrixStack, ownBoard.meleeBoard);
        renderCardsFromList(matrixStack, ownBoard.rangedBoard);
        renderCardsFromList(matrixStack, enemyBoard.meleeBoard);
        renderCardsFromList(matrixStack, enemyBoard.rangedBoard);
        renderCardsFromList(matrixStack, enemyBoard.specialBoard);
        renderCardsFromList(matrixStack, ownBoard.specialBoard);
    }
    public void renderOtherStuff(MatrixStack matrixStack) {
        this.minecraft.getTextureManager().getTexture(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/pass.png"));
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.PassX, offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY, 0, 0, MinecardTableImageLocations.PassWidth, MinecardTableImageLocations.PassHeight);
        this.minecraft.getTextureManager().getTexture(new ResourceLocation(""));
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.guiwidth- MinecardCard.cardwidth-10, offsetY+ MinecardTableImageLocations.guiheight- MinecardCard.cardheight-10, 0, 0, MinecardCard.cardwidth, MinecardCard.cardheight);
        this.blit(matrixStack, offsetX+ MinecardTableImageLocations.guiwidth- MinecardCard.cardwidth-10, offsetY+10, 0, 0, MinecardCard.cardwidth, MinecardCard.cardheight);
        this.minecraft.getTextureManager().getTexture(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/heart.png"));
        if (ownBoard.lifePoints == 2) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (ownBoard.lifePoints == 1) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        if (enemyBoard.lifePoints == 2) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (enemyBoard.lifePoints == 1) {
            this.blit(matrixStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        this.minecraft.getTextureManager().getTexture(new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/grey.png"));
        this.blit(matrixStack, offsetX+MinecardTableImageLocations.optionsX, offsetY+MinecardTableImageLocations.optionsY, 0, 0, MinecardTableImageLocations.optionsSide, MinecardTableImageLocations.optionsSide);
    }

    public void renderWindow(MatrixStack matrixStack, int offsetX, int offsetY) {
        this.minecraft.getTextureManager().getTexture(GUI);
        this.blit(matrixStack, offsetX, offsetY, 0, 0, MinecardTableImageLocations.guiwidth, MinecardTableImageLocations.guiheight);
    }


    public void renderValues(MatrixStack matrixStack, int offsetX, int offsetY) {
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(ownBoard.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(ownBoard.deck.size()).length())-16, offsetY+ MinecardTableImageLocations.guiheight-18, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(enemyBoard.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(ownBoard.deck.size()).length())-16, offsetY+ MinecardCard.cardheight+2, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(enemyBoard.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(enemyBoard.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2-20, -1);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty(Integer.toString(ownBoard.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(ownBoard.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2+20, -1);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            Minecraft.getInstance().setScreen(new DeckBuilderGui());
            return true;
        } else if (keyCode == 81) {
            ownBoard.drawCard();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int cardindex = getTouchingCardFromList(mouseX, mouseY, ownBoard.hand);
        if (button == 0) { //left mouse
            if (ownBoard.isYourTurn) {
                if (ownBoard.playCard(cardindex)) {
                    ownBoard.isYourTurn=false;
                    enemyBoard.isYourTurn=true;
                }
                if (ownBoard.hand.size() == 0) {
                    ownBoard.hasPassed = true;
                }
            }
            if (isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
                ownBoard.hasPassed = true;
            }
        }
        /*
        if (button == 1) { // right mouse
        }
         */
        return true;
    }

    public void enemyPlay() {
        if (enemyBoard.hand.size() == 0) {
            enemyBoard.hasPassed = true;
            enemyBoard.isYourTurn = false;
        }
        if (enemyBoard.isYourTurn) {
            enemyBoard.playCard(ThreadLocalRandom.current().nextInt(0, enemyBoard.hand.size()));
        }
        enemyBoard.isYourTurn = false;
        ownBoard.isYourTurn = true;
    }
    public void endRound() {
        if (ownBoard.getStrength() > enemyBoard.getStrength()) {
            enemyBoard.lifePoints --;
        }
        if (ownBoard.getStrength() < enemyBoard.getStrength()) {
            ownBoard.lifePoints --;
        }
        if (ownBoard.getStrength() == enemyBoard.getStrength()) {
            ownBoard.lifePoints --;
            enemyBoard.lifePoints --;
        }
        enemyBoard.hasPassed = false;
        ownBoard.hasPassed = false;
        enemyBoard.clearBoard();
        ownBoard.clearBoard();
        enemyBoard.drawCard(2);
        ownBoard.drawCard(2);
    }
    public void endGame() {
        gameHasEnded = true;
        if (enemyBoard.lifePoints == 0 && ownBoard.lifePoints == 0) {
            Minecraft.getInstance().player.chat("It is a Draw!");
        } else if(enemyBoard.lifePoints == 0) {
            Minecraft.getInstance().player.chat("You have won!");
        } else if(ownBoard.lifePoints == 0) {
            Minecraft.getInstance().player.chat("You Have Lost!");
        }
        onClose();
    }
    
    public void loadGame(MinecardBoardState[] boardstate) {
        ownBoard = boardstate[0];
        enemyBoard = boardstate[1];
    }

    @Override
    public void onClose() {
        if (!gameHasEnded) {
            GlobalValues.savedBoardTemp.put(Minecraft.getInstance().player, new MinecardBoardState[]{ownBoard, enemyBoard});
        } else {
            GlobalValues.savedBoardTemp.remove(Minecraft.getInstance().player);
        }
        super.onClose();
    }

}
