package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.core.init.GlobalValues;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMinecardScreen extends Screen {

    public int offsetX;
    public int offsetY;
    boolean isFirstTimeInit = true;

    protected AbstractMinecardScreen(ITextComponent titleIn) {
        super(titleIn);
    }
    public abstract int[] getCardPosInList(int i, List<MinecardCard> list);

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
    public void renderCardTooltip(MatrixStack matrixStack, List<MinecardCard> list, int mouseX, int mouseY) {
        if (getTouchingCardFromList(mouseX, mouseY, list) != -1) { // && ownBoard.isYourTurn
            int xMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[0];
            int yMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[1];
            renderComponentTooltip(matrixStack, list.get(getTouchingCardFromList(mouseX, mouseY, list)).getTooltip(), mouseX, mouseY);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.offsetX = (this.width - MinecardTableImageLocations.guiwidth) / 2;
        this.offsetY = (this.height - MinecardTableImageLocations.guiheight) / 2;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public boolean isWithinBoundingBox(double x, double y, int xMin, int xMax, int yMin, int yMax) {
        return x > xMin && x < xMax && y > yMin && y < yMax;
    }
    public boolean isWithinCardBoundingBox(double x,double y, int xMin, int yMin) {
        return isWithinBoundingBox(x, y, xMin, xMin+ MinecardCard.cardwidth,yMin, yMin+ MinecardCard.cardheight);
    }

    public int getTouchingCardFromList(double mouseX, double mouseY, List<MinecardCard> list){
        int index = 0;
        int touchedcard = -1;
        for (MinecardCard card:list) {
            if (isWithinCardBoundingBox(mouseX, mouseY, getCardPosInList(index, list)[0], getCardPosInList(index, list)[1])) {
                touchedcard = index;
            }
            index ++;
        }
        index = 0;
        return touchedcard;
    }

    public void renderCardsFromList (MatrixStack matrixStack, List<MinecardCard> list) {
        int index = 0;
        for (MinecardCard card:list) {
            assert this.minecraft != null;
            this.minecraft.getTextureManager().bind(card.getTexture());
            int x = getCardPosInList(index, list)[0];
            int y = getCardPosInList(index, list)[1];
            this.blit(matrixStack, x, y, 0, 0, MinecardCard.cardwidth, MinecardCard.cardheight);
            this.font.draw(matrixStack, Integer.toString(card.getStrength()), x+ MinecardCard.cardwidth-6, y+ MinecardCard.cardheight-8, 0);
            index ++;
        }
        index = 0;

    }

    public void renderCardHighlightFromList(MatrixStack matrixStack, int mouseX, int mouseY, List<MinecardCard> list) {
        if (getTouchingCardFromList(mouseX, mouseY, list) != -1) { // && ownBoard.isYourTurn
            int xMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[0];
            int yMin = getCardPosInList(getTouchingCardFromList(mouseX, mouseY, list), list)[1];
            fillGradient(matrixStack, xMin, yMin, xMin+ MinecardCard.cardwidth, yMin+ MinecardCard.cardheight, -1072689136, -804253680);
        }
    }


}
