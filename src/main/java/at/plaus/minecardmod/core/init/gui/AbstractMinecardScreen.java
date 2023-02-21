package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.core.init.GlobalValues;
import at.plaus.minecardmod.core.init.events.ModClientEvents;
import at.plaus.minecardmod.core.init.menu.MinecardScreenMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;



public abstract class AbstractMinecardScreen extends AbstractContainerScreen<MinecardScreenMenu> implements MenuProvider {

    public int offsetX;
    public int offsetY;
    boolean isFirstTimeInit = true;

    public AbstractMinecardScreen(MinecardScreenMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }


    public abstract int[] getCardPosInList(int i, List<Card> list);

    @Override
    protected void init() {
        super.init();
        if (this.isFirstTimeInit){
            startup();
        }
        this.isFirstTimeInit = false;
    }

    public void startup() {
        GlobalValues.switchToOther = false;
    }
    public void renderCardTooltip(PoseStack poseStack, List<Card> list, int mouseX, int mouseY) {
        if (getTouchingCardFromList(mouseX, mouseY, list) != -1) { // && ownBoard.isYourTurn
            int xMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[0];
            int yMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[1];
            ModClientEvents.eventCard = list.get(getTouchingCardFromList(mouseX, mouseY, list));
            renderComponentTooltip(poseStack, list.get(getTouchingCardFromList(mouseX, mouseY, list)).getTooltip1(), mouseX, mouseY);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.offsetX = (this.width - MinecardTableImageLocations.guiwidth) / 2;
        this.offsetY = (this.height - MinecardTableImageLocations.guiheight) / 2;
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public boolean isWithinBoundingBox(double x, double y, int xMin, int xMax, int yMin, int yMax) {
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }
    public boolean isWithinCardBoundingBox(double x,double y, int xMin, int yMin) {
        return isWithinBoundingBox(x, y, xMin, xMin+ Card.cardwidth,yMin, yMin+ Card.cardheight);
    }

    //-1 if touching no cards
    public int getTouchingCardFromList(double mouseX, double mouseY, List<Card> list){
        int index = 0;
        int touchedcard = -1;
        for (Card card:list) {
            if (isWithinCardBoundingBox(mouseX, mouseY, getCardPosInList(index, list)[0], getCardPosInList(index, list)[1])) {
                touchedcard = index;
            }
            index ++;
        }
        index = 0;
        return touchedcard;
    }
    public int getTouchingPartCardFromList(double mouseX, double mouseY, List<Card> list){
        int index = 0;
        int touchedcard = -1;
        for (Card card:list) {
            if (isWithinBoundingBox(mouseX, mouseY, getCardPosInList(index, list)[0],getCardPosInList(index, list)[0] + Card.cardwidth, getCardPosInList(index, list)[1], getCardPosInList(index, list)[1] + DeckBuilderGui.cardOffset)) {
                touchedcard = index;
            }
            if (index == list.size()-1 && isWithinBoundingBox(mouseX, mouseY, getCardPosInList(index, list)[0],getCardPosInList(index, list)[0] + Card.cardwidth, getCardPosInList(index, list)[1], getCardPosInList(index, list)[1] + Card.cardheight)) {
                touchedcard = index;
            }
            index ++;
        }
        index = 0;
        return touchedcard;
    }

    public void renderCardsFromList (PoseStack poseStack, List<Card> list) {
        int index = 0;
        for (Card card:list) {
            assert this.minecraft != null;
            int x = getCardPosInList(index, list)[0];
            int y = getCardPosInList(index, list)[1];

            RenderSystem.setShaderTexture(0,card.getTexture());
            GuiComponent.blit(poseStack, x, y, 0, (float)0, (float)0, Card.cardwidth, Card.cardheight, 64, 64);

            RenderSystem.setShaderTexture(0,card.frame);
            GuiComponent.blit(poseStack, x, y, 0, (float)5, (float)5, Card.cardwidth, Card.cardheight, 64, 64);

            this.font.draw(poseStack, Integer.toString(card.getStrength()), x+ Card.cardwidth-6*Integer.toString(card.getStrength()).length(), y+ Card.cardheight-8, 0);
            index ++;
        }
        index = 0;

    }

    public void renderCardHighlightFromList(PoseStack poseStack, int mouseX, int mouseY, List<Card> list) {
        if (getTouchingCardFromList(mouseX, mouseY, list) != -1) { // && ownBoard.isYourTurn
            int xMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[0];
            int yMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[1];
            fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ Card.cardheight, -1072689136, -804253680);
        }
    }

    public void renderPartCardHighlightFromList(PoseStack poseStack, int mouseX, int mouseY, List<Card> list) {
        if (getTouchingPartCardFromList(mouseX, mouseY, list) != -1) { // && ownBoard.isYourTurn
            if (getTouchingPartCardFromList(mouseX, mouseY, list) < list.size()-1) {
                int xMin = getCardPosInList(getTouchingPartCardFromList(mouseX, mouseY, list), list)[0];
                int yMin = getCardPosInList(getTouchingPartCardFromList(mouseX, mouseY, list), list)[1];
                fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ DeckBuilderGui.cardOffset, -1072689136, -804253680);
            } else {
                int xMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[0];
                int yMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[1];
                fillGradient(poseStack, xMin, yMin, xMin+ Card.cardwidth, yMin+ Card.cardheight, -1072689136, -804253680);
            }

        }
    }


}
