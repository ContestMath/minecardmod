package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.events.ModClientEvents;
import at.plaus.minecardmod.core.init.menu.MinecardScreenMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.Nullable;

import java.util.List;



public abstract class AbstractMinecardScreen extends AbstractContainerScreen<MinecardScreenMenu> implements MenuProvider {

    public int offsetX;
    public int offsetY;
    public static boolean isFirstTimeInit = true;
    abstract List<Card> getAllCards();
    abstract List<Card> getAllrenderableCards();

    public AbstractMinecardScreen(MinecardScreenMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }


    public abstract int[] getCardPos(Card card);


    public void startup() {}

    public void renderCardTooltip(PoseStack poseStack, int mouseX, int mouseY) {
        if (getTouchingCard(mouseX, mouseY) != null) { // && ownBoard.isYourTurn
            int xMin = getCardPos(getTouchingCard(mouseX, mouseY))[0];
            int yMin = getCardPos(getTouchingCard(mouseX, mouseY))[1];
            ModClientEvents.eventCard = getTouchingCard(mouseX, mouseY);
            renderComponentTooltip(poseStack, getTouchingCard(mouseX, mouseY).getTooltip1(), mouseX, mouseY);
            ModClientEvents.eventCard = null;
        }
    }

    @Override
    protected void init() {
        super.init();
        if (isFirstTimeInit){
            startup();
        }
        isFirstTimeInit = false;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.offsetX = (this.width - MinecardTableImageLocations.guiwidth) / 2;
        this.offsetY = (this.height - MinecardTableImageLocations.guiheight) / 2;
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public boolean isWithinBoundingBox(double x, double y, int xMin, int xMax, int yMin, int yMax) {
        return x >= xMin && x < xMax && y >= yMin && y < yMax;
    }
    public boolean isWithinCardBoundingBox(double x,double y, int xMin, int yMin) {
        return isWithinBoundingBox(x, y, xMin, xMin+ Card.cardwidth, yMin, yMin+ Card.cardheight);
    }

    //-1 if touching no cards
    @Nullable
    public Card getTouchingCard(double mouseX, double mouseY){
        int index = 0;
        for (Card card:getAllrenderableCards()) {
            if (isWithinCardBoundingBox(mouseX, mouseY, getCardPos(card)[0], getCardPos(card)[1])) {
                return card;
            }
            index ++;
        }
        index = 0;
        return null;
    }


    public Card getTouchingPartCardFromList(double mouseX, double mouseY, List<Card> list){
        int index = 0;
        for (Card card:list) {
            if (isWithinBoundingBox(mouseX, mouseY, getCardPos(card)[0], getCardPos(card)[0] + Card.cardwidth, getCardPos(card)[1], getCardPos(card)[1] + DeckBuilderGui.cardOffset)) {
                return card;
            }
            if (index == list.size()-1 && isWithinBoundingBox(mouseX, mouseY, getCardPos(card)[0], getCardPos(card)[0] + Card.cardwidth, getCardPos(card)[1], getCardPos(card)[1] + Card.cardheight)) {
                return card;
            }
            index ++;
        }
        return null;
    }

    public void renderCardsFromList (PoseStack poseStack, List<Card> list) {
        int index = 0;
        for (Card card:list) {
            int x = getCardPos(card)[0];
            int y = getCardPos(card)[1];

            RenderSystem.setShaderTexture(0,card.getTexture());
            GuiComponent.blit(poseStack, x, y, 0, (float)0, (float)0, Card.cardwidth, Card.cardheight, 64, 64);

            RenderSystem.setShaderTexture(0, new ResourceLocation(Minecardmod.MOD_ID,card.frameString));
            GuiComponent.blit(poseStack, x, y, 0, (float)5, (float)5, Card.cardwidth, Card.cardheight, 64, 64);

            this.font.draw(poseStack, Integer.toString(card.getStrength()), x+ Card.cardwidth-6*Integer.toString(card.getStrength()).length(), y+ Card.cardheight-8, 0);

            index ++;
        }
        index = 0;

    }

    public void renderCardsFromList (PoseStack poseStack, List<Card> list, List<Card> inDeck) {
        int index = 0;
        for (Card card:list) {
            int x = getCardPos(card)[0];
            int y = getCardPos(card)[1];

            RenderSystem.setShaderTexture(0,card.getTexture());
            GuiComponent.blit(poseStack, x, y, 0, (float)0, (float)0, Card.cardwidth, Card.cardheight, 64, 64);

            RenderSystem.setShaderTexture(0, new ResourceLocation(Minecardmod.MOD_ID,card.frameString));
            GuiComponent.blit(poseStack, x, y, 0, (float)5, (float)5, Card.cardwidth, Card.cardheight, 64, 64);
            this.font.draw(poseStack, Integer.toString(card.getStrength()), x+ Card.cardwidth-6*Integer.toString(card.getStrength()).length(), y+ Card.cardheight-8, 0);
            int numberInDeck = 0;
            for (Card i:inDeck) {
               if (i.getClass().equals(card.getClass())) {
                   numberInDeck ++;
               }
            }
            this.font.draw(poseStack, Integer.toString(card.getNumberUnlocked()-numberInDeck), x, y, 0);
            index ++;
        }
        index = 0;
    }

    public void renderCardHighlightFromList(PoseStack poseStack, int mouseX, int mouseY, List<Card> cards) {
        if (getTouchingCard(mouseX, mouseY) != null && cards.contains(getTouchingCard(mouseX, mouseY))) { // && ownBoard.isYourTurn
            Card card = getTouchingCard(mouseX, mouseY);
            int xMin = getCardPos(getTouchingCard(mouseX, mouseY))[0];
            int yMin = getCardPos(getTouchingCard(mouseX, mouseY))[1];
            fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ Card.cardheight, -1072689136, -804253680);
        }
    }

    public void renderCardHighlightFromList(PoseStack poseStack, int mouseX, int mouseY, Boardstate board) {
        if (getTouchingCard(mouseX, mouseY) != null) { // && ownBoard.isYourTurn
            Card card = getTouchingCard(mouseX, mouseY);
            assert card != null;
            if (card.isTargetable(board)) {
                int xMin = getCardPos(getTouchingCard(mouseX, mouseY))[0];
                int yMin = getCardPos(getTouchingCard(mouseX, mouseY))[1];
                fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ Card.cardheight, -1072689136, -804253680);
            }
        }
    }


    public void renderPartCardHighlightFromList(PoseStack poseStack, int mouseX, int mouseY, List<Card> list) {
        if (getTouchingPartCardFromList(mouseX, mouseY, list) != null) { // && ownBoard.isYourTurn
            if (list.indexOf(getTouchingPartCardFromList(mouseX, mouseY, list)) < list.size()-1) {
                int xMin = getCardPos(getTouchingPartCardFromList(mouseX, mouseY, list))[0];
                int yMin = getCardPos(getTouchingPartCardFromList(mouseX, mouseY, list))[1];
                fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ DeckBuilderGui.cardOffset, -1072689136, -804253680);
            } else {
                int xMin = getCardPos(getTouchingCard(mouseX, mouseY))[0];
                int yMin = getCardPos(getTouchingCard(mouseX, mouseY))[1];
                fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ Card.cardheight, -1072689136, -804253680);
            }

        }
    }


}
