package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.client.ClientDeckData;
import at.plaus.minecardmod.core.init.GlobalValues;
import at.plaus.minecardmod.core.init.MinecardRules;
import at.plaus.minecardmod.core.init.gui.cards.BlueCard;
import at.plaus.minecardmod.core.init.gui.cards.BrownCard;
import at.plaus.minecardmod.core.init.gui.cards.YellowCard;
import at.plaus.minecardmod.core.init.menu.MinecardScreenMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MinecardTableGui extends AbstractMinecardScreen {

    private final ResourceLocation GUI1 = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui1.png");

    private final ResourceLocation GUI2 = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui2.png");

    
    public Boardstate board = new Boardstate();

    int index = 0;
    public int numberStringWidth = 6;
    public boolean gameHasEnded = false;
    public final MinecardScreenMenu menu;
    public final Inventory inv;
    public final Component component;
    public static boolean cardWasPlayed;

    public static final Component name = Component.translatable("tooltip.minecardmod.minecard_table");

    public MinecardTableGui(MinecardScreenMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inv = inventory;
        this.menu = menu;
        this.component = component;

    }


    public int[] getCardPosInList(int i, List<Card> list) {
        int x = offsetX + MinecardTableImageLocations.guiwidth/2 - 5 * (list.size()-1) + 10*i + Card.cardwidth*i - Card.cardwidth/2*list.size();
        int yStart = offsetY+ MinecardTableImageLocations.guiheight- Card.cardheight;
        int space = 5;
        if (list.equals(board.own.hand)) {
            return new int[]{x-15, yStart-10};
        } else if (list.equals(board.own.specialBoard)) {
            return new int[]{x, yStart- Card.cardheight-3*space};
        } else if (list.equals(board.own.rangedBoard)) {
            return new int[]{x, yStart- Card.cardheight*2-4*space};
        } else if (list.equals(board.own.meleeBoard)) {
            return new int[]{x, yStart - Card.cardheight *3-5*space};
        } else if (list.equals(board.enemy.meleeBoard)) {
            return new int[]{x, yStart- Card.cardheight*4-6*space};
        } else if (list.equals(board.enemy.rangedBoard)) {
            return new int[]{x, yStart- Card.cardheight*5-7*space};
        } else if (list.equals(board.enemy.specialBoard)) {
            return new int[]{x, yStart- Card.cardheight*6-8*space};
        }
        else return new int[]{0, 0};
    }


    @Override
    public void startup() {
        Player p = Minecraft.getInstance().player;

        if (GlobalValues.savedBoardTemp.containsKey(p)) {
            loadGame(GlobalValues.savedBoardTemp.get(p));
        } else {
            board.enemy.isYourTurn = false;
            String deckString = ClientDeckData.getDeck();
            if (!Objects.equals(deckString, "")) {

                board.own.deck = DeckBuilderGui.stringToDeck(deckString);
                
                Collections.shuffle(board.own.deck, new Random());
            } else {
                for (int i = 0; i < 10; i++) {
                    board.own.deck.push(new BlueCard());
                    board.own.deck.push(new YellowCard());
                    board.own.deck.push(new BrownCard());
                }
            }
            for (int i = 0; i < 10; i++) {
                board.enemy.deck.push(new BlueCard());
                board.enemy.deck.push(new YellowCard());
                board.enemy.deck.push(new BrownCard());
            }

            board.own.drawCard(MinecardRules.startingHandsize);
            board.enemy.drawCard(MinecardRules.startingHandsize);
            //Minecraft.getInstance().player.sendSystemMessage(Component.literal(board.own.hand.toString()));
        }
        super.startup();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    public void playLoop() {
        if (board.enemy.hand.isEmpty()) {
            board.enemy.hasPassed = true;
        }
        if (board.own.hand.isEmpty()) {
            board.own.hasPassed = true;
        }
        if (board.own.hasPassed && board.enemy.hasPassed) {
            endRound();
        }
        if (board.own.lifePoints == 0 || board.enemy.lifePoints == 0) {
            endGame();
        }
        if (board.enemy.hasPassed) {
            board.enemy.isYourTurn = false;
            board.own.isYourTurn = true;
        }
        if (board.own.hasPassed) {
            board.own.isYourTurn = false;
            board.enemy.isYourTurn = true;
        }
        enemyPlay();
    }

    @Override
    public void render(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(PoseStack, mouseX, mouseY, partialTicks);
        this.renderBackground(PoseStack); //black Background
        this.renderWindow(PoseStack, offsetX, offsetY);
        renderOtherStuff(PoseStack);
        renderCards(PoseStack);
        renderHighlight(PoseStack, mouseX, mouseY);
        this.font.draw(PoseStack, Component.literal("Minecard table"), offsetX+2, offsetY+2, -1);
        renderValues(PoseStack, offsetX, offsetY);
        renderAllTooltipp(PoseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
    }

    private void renderHighlight(PoseStack PoseStack, int mouseX, int mouseY) {
        if (!board.gamePaused) {
            renderCardHighlightFromList(PoseStack, mouseX, mouseY, board.own.hand, board);
        } else {
            for (List<Card> list:board.selectionTargets) {
                renderCardHighlightFromList(PoseStack, mouseX, mouseY, list, board);
            }
        }
        if (board.own.isYourTurn && isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
            fillGradient(PoseStack, offsetX+20, offsetY+ MinecardTableImageLocations.guiheight-25-20, offsetX+20+40, offsetY+ MinecardTableImageLocations.guiheight-25-20+25, -1072689136, -804253680);
        }
        if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)) {
            fillGradient(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
        }
    }

    private void renderAllTooltipp(PoseStack PoseStack, int mouseX, int mouseY) {
        if (isWithinBoundingBox(mouseX, mouseY,offsetX + MinecardTableImageLocations.optionsX, offsetX +MinecardTableImageLocations.optionsX+MinecardTableImageLocations.optionsSide, offsetY + MinecardTableImageLocations.optionsY,  offsetY +MinecardTableImageLocations.optionsY+MinecardTableImageLocations.optionsSide)) {
            //renderTooltip(PoseStack, Component.literal("hi"), mouseX, mouseY);
            renderTooltip(PoseStack, Component.translatable("tooltip.gui.options"), mouseX, mouseY);
            RenderSystem.setShaderTexture(0, GUI1);
            this.blit(PoseStack, mouseX, mouseY, 0, 0, 20, 20);
        }
        renderCardTooltip(PoseStack, board.own.hand, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.own.meleeBoard, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.own.rangedBoard, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.own.specialBoard, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.enemy.meleeBoard, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.enemy.rangedBoard, mouseX, mouseY);
        renderCardTooltip(PoseStack, board.enemy.specialBoard, mouseX, mouseY);
    }

    public void renderCards (PoseStack PoseStack) {
        renderCardsFromList(PoseStack, board.own.hand);
        renderCardsFromList(PoseStack, board.own.meleeBoard);
        renderCardsFromList(PoseStack, board.own.rangedBoard);
        renderCardsFromList(PoseStack, board.enemy.meleeBoard);
        renderCardsFromList(PoseStack, board.enemy.rangedBoard);
        renderCardsFromList(PoseStack, board.enemy.specialBoard);
        renderCardsFromList(PoseStack, board.own.specialBoard);
    }

    public void renderOtherStuff(PoseStack PoseStack) {
        RenderSystem.setShaderTexture(0,new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/pass.png"));
        this.blit(PoseStack, offsetX+ MinecardTableImageLocations.PassX, offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY, 0, 0, MinecardTableImageLocations.PassWidth, MinecardTableImageLocations.PassHeight);
        RenderSystem.setShaderTexture(0,new ResourceLocation(""));
        this.blit(PoseStack, offsetX+ MinecardTableImageLocations.guiwidth- Card.cardwidth-10, offsetY+ MinecardTableImageLocations.guiheight- Card.cardheight-10, 0, 0, Card.cardwidth, Card.cardheight);
        //this.blit(PoseStack, offsetX+ MinecardTableImageLocations.guiwidth- Card.cardwidth-10, offsetY+10, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
        //change button
        this.blit(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
        RenderSystem.setShaderTexture(0,new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/heart.png"));
        if (board.own.lifePoints == 2) {
            this.blit(PoseStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (board.own.lifePoints == 1) {
            this.blit(PoseStack, offsetX+ MinecardTableImageLocations.ownHeartX, offsetY+ MinecardTableImageLocations.ownHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        if (board.enemy.lifePoints == 2) {
            this.blit(PoseStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, MinecardTableImageLocations.heartWidth, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        } else if (board.enemy.lifePoints == 1) {
            this.blit(PoseStack, offsetX+ MinecardTableImageLocations.enemyHeartX, offsetY+ MinecardTableImageLocations.enemyHeartY, 0, 0, MinecardTableImageLocations.heartWidth, MinecardTableImageLocations.heartHeight);
        }
        RenderSystem.setShaderTexture(0,new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/grey.png"));
        this.blit(PoseStack, offsetX+MinecardTableImageLocations.optionsX, offsetY+MinecardTableImageLocations.optionsY, 0, 0, MinecardTableImageLocations.optionsSide, MinecardTableImageLocations.optionsSide);
    }

    public void renderWindow(PoseStack PoseStack, int offsetX, int offsetY) {
        RenderSystem.setShaderTexture(0, GUI1);
        this.blit(PoseStack, offsetX, offsetY, 0, 0, 256, MinecardTableImageLocations.guiheight);
        RenderSystem.setShaderTexture(0,GUI2);
        this.blit(PoseStack, offsetX+256, offsetY, 0, 0, MinecardTableImageLocations.guiwidth-256, MinecardTableImageLocations.guiheight);
    }

    public void renderValues(PoseStack PoseStack, int offsetX, int offsetY) {
        this.font.draw(PoseStack, Component.literal(Integer.toString(board.own.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.deck.size()).length()/2)-16, offsetY+ MinecardTableImageLocations.guiheight-18, -1);
        this.font.draw(PoseStack, Component.literal(Integer.toString(board.enemy.deck.size())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.deck.size()).length()/2)-16, offsetY+ Card.cardheight+2, -1);
        this.font.draw(PoseStack, Component.literal(Integer.toString(board.enemy.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.enemy.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2-20, -1);
        this.font.draw(PoseStack, Component.literal(Integer.toString(board.own.getStrength())), offsetX+ MinecardTableImageLocations.guiwidth-this.numberStringWidth*(Integer.toString(board.own.getStrength()).length()), offsetY+ MinecardTableImageLocations.guiheight/2+20, -1);
        this.font.draw(PoseStack, Component.literal(Integer.toString(board.enemy.hand.size())), offsetX+ (MinecardTableImageLocations.guiwidth-this.numberStringWidth*Integer.toString(board.own.getStrength()).length())/2, offsetY+5, -1);

        if (!board.selectionTargets.isEmpty()) {
            this.font.draw(PoseStack, Component.literal("Press space to decline to select target(s)"), 5, this.height-8, -1);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            if (!board.selectionListeners.isEmpty()) {
                board.selectionListeners.remove(0);
                if (board.selectionListeners.isEmpty()) {
                    board.gamePaused = false;
                    board.selectionListeners = new ArrayList<>();
                    board.selectionTargets = new ArrayList<>();
                }
                if (!board.gamePaused) {
                    board.switchTurn();
                }
                playLoop();
            }
            return true;
        } else if (keyCode == 81) {
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (!board.gamePaused) {
                int cardindex = getTouchingCardFromList(mouseX, mouseY, board.own.hand);
                if (board.own.isYourTurn) {
                    if (cardindex != -1){
                        board = board.playCardFromHand(cardindex, Boardstate.Player.OWN);
                        if (!board.gamePaused && cardWasPlayed) {
                            board.switchTurn();
                            playLoop();
                        }
                    }
                }
            } else {
                int cardindex = -1;
                for (List<Card> list:board.selectionTargets) {
                    cardindex = getTouchingCardFromList(mouseX, mouseY, list);
                    if (cardindex != -1 && board.own.isYourTurn) {
                        board = list.get(cardindex).selected(board);
                        if (!board.gamePaused) {
                            board.switchTurn();
                        }
                        playLoop();
                    }
                }


            }
            if (isWithinBoundingBox(mouseX, mouseY, offsetX+ MinecardTableImageLocations.PassX, offsetX+ MinecardTableImageLocations.PassX+ MinecardTableImageLocations.PassWidth,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassHeight- MinecardTableImageLocations.PassY,offsetY+ MinecardTableImageLocations.guiheight- MinecardTableImageLocations.PassY)) {
                board.own.hasPassed = true;
                playLoop();
            }

            if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)){
                onCloseOrSwitch();
                Minecraft.getInstance().setScreen(new DeckBuilderGui(menu, inv, component));
            }
        }


        return true;
    }

    public void enemyPlay() {
        if (board.enemy.isYourTurn && !board.gamePaused) {
            board = board.playCardFromHand(ThreadLocalRandom.current().nextInt(0, board.enemy.hand.size()), Boardstate.Player.ENEMY);
            if (board.enemy.hand.isEmpty()) {
                board.enemy.hasPassed = true;
            }
            board.switchTurn();
            playLoop();
        }
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
        board = board.clearBoard();
        board.enemy.drawCard(2);
        board.own.drawCard(2);
    }
    public void endGame() {
        gameHasEnded = true;
        if (board.enemy.lifePoints == 0 && board.own.lifePoints == 0) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("It is a draw!"));
        } else if(board.enemy.lifePoints == 0) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("You have won!"));
        } else if(board.own.lifePoints == 0) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("You have lost"));
        }
        isFirstTimeInit = true;
        onClose();
    }

    public void loadGame(Boardstate boardstate) {
        board = boardstate;
    }

    public void onCloseOrSwitch() {
        if (!gameHasEnded) {
            GlobalValues.savedBoardTemp.put(Minecraft.getInstance().player, board);
        } else {
            GlobalValues.savedBoardTemp.remove(Minecraft.getInstance().player);
        }
    }

    @Override
    public void onClose() {
        onCloseOrSwitch();
        super.onClose();
    }

    @Override
    public Component getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return createMenu();
    }

    @Nullable
    public AbstractContainerMenu createMenu() {
        return null;
    }
}
