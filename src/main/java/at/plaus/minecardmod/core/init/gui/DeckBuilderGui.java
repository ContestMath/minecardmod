package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.GlobalValues;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilderGui extends AbstractMinecardScreen{

    public int page = 0;
    public static final int cardOffset = 5;
    public final int rows = 5;
    public final int collums = 8;
    public List<MinecardCard> deck = new ArrayList<MinecardCard>();
    public static final ITextComponent title = new TranslationTextComponent("tooltip.minecardmod.minecard_table");

    private final ResourceLocation GUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui.png");

    public DeckBuilderGui() {
        super(title);
    }

    @Override
    public int[] getCardPosInList(int i, List<MinecardCard> list) {
        int space = 5;
        if (list.equals(deck)) {
            return new int[] {
                    MinecardTableImageLocations.guiwidth+offsetX-MinecardCard.cardwidth-10,
                    offsetY+10+i*cardOffset
            };
        } else if (rows*collums*page <= i && i < rows*collums*(page+1)) {
            return new int[] {
                    10+offsetX+(MinecardCard.cardwidth+space)*(i%collums),
                    15+offsetY+(MinecardCard.cardheight+space)*(i/collums)
            };
        }

        //Minecraft.getInstance().player.sendChatMessage(list.toString());
        return new int[]{0, 0};
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderBackground(matrixStack); //black Background
        this.renderWindow(matrixStack, offsetX, offsetY);
        renderCards(matrixStack, mouseX, mouseY);
        renderDeck(matrixStack);
        renderHighlight(matrixStack, mouseX, mouseY);
        renderCardTooltip(matrixStack, MinecardCard.getListOfAllCards(), mouseX, mouseY);
        this.font.draw(matrixStack, ITextComponent.nullToEmpty("Deck builder"), offsetX+2, offsetY+2, -1);
    }

    private void renderHighlight(MatrixStack matrixStack, int mouseX, int mouseY) {
        renderCardHighlightFromList(matrixStack, mouseX, mouseY, MinecardCard.getListOfAllCards());
        if (getTouchingPartCardFromList(mouseX, mouseY, deck) != -1) {
            if (getTouchingPartCardFromList(mouseX, mouseY, deck) == deck.size()) {
                renderCardHighlightFromList(matrixStack, mouseX, mouseY, deck);
            }
        }
    }

    private void renderCards(MatrixStack matrixStack, int mouseX, int mouseY) {
        renderCardsFromList(matrixStack, deck);
        renderPartCardHighlightFromList(matrixStack, mouseX, mouseY, deck);
        renderCardsFromList(matrixStack, MinecardCard.getListOfAllCards());
    }

    private void renderDeck(MatrixStack matrixStack) {
        renderCardsFromList(matrixStack, MinecardCard.getListOfAllCards());
    }


    public void renderWindow(MatrixStack matrixStack, int offsetX, int offsetY) {
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, offsetX, offsetY, 0, 0, MinecardTableImageLocations.guiwidth, MinecardTableImageLocations.guiheight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<MinecardCard> list = MinecardCard.getListOfAllCards();
        int cardindex = getTouchingCardFromList(mouseX, mouseY, list);
        if (button == 0 && cardindex != -1) { //left mouse
            deck.add(list.get(cardindex));
        }

        cardindex = getTouchingPartCardFromList(mouseX, mouseY, list);
        if (button == 0 && cardindex != -1) { //left mouse
            deck.remove(list.get(cardindex));
        }
        cardindex = getTouchingCardFromList(mouseX, mouseY, list);
        if (button == 0 && cardindex != -1) { //left mouse
            if (getTouchingPartCardFromList(mouseX, mouseY, deck) == deck.size()) {
                deck.remove(list.get(cardindex));
            }
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            onCloseOrSwitch();
            Minecraft.getInstance().setScreen(new MinecardTableGui());
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public void onCloseOrSwitch() {
        GlobalValues.deck1.put(Minecraft.getInstance().player, deck);
    }
    @Override
    public void onClose() {
        onCloseOrSwitch();
        super.onClose();
    }
}
