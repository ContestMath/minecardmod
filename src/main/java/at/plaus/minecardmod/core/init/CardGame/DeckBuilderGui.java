package at.plaus.minecardmod.core.init.CardGame;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.DeckC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class DeckBuilderGui extends AbstractMinecardScreen {

    public int page = 0;
    public static final int cardOffset = 5;
    public final int rows = 5;
    public final int collums = 8;
    public List<Card> deck = new ArrayList<Card>();
    public List<Card> cardSelection = Card.getListOfAllNonTokenCards();
    public static final Component name = Component.translatable("tooltip.minecardmod.minecard_table");

    private static final ResourceLocation GUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui.png");
    private static final ResourceLocation oGUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/untitled.png");

    @Override
    List<Card> getAllCards() {
        return cardSelection;
    }

    @Override
    List<Card> getAllrenderableCards() {
        List<Card> list = new ArrayList<>();
        list.addAll(deck);
        list.addAll(cardSelection);
        return list;
    }

    public DeckBuilderGui() {
        super(Component.literal("I have no idea what this component is for"));
    }

    @Override
    public int[] getCardPos(Card card) {
        int space = 5;
        int i = cardSelection.indexOf(card);
        if (deck.contains(card)) {
            return new int[] {
                    MinecardTableImageLocations.guiwidth+offsetX- Card.cardwidth-10,
                    offsetY+10+ deck.indexOf(card)*cardOffset
            };
        } else if (rows*collums*page <= i && i < rows*collums*(page+1)) {
            return new int[] {
                    10+offsetX+(Card.cardwidth+space)*(i%collums),
                    15+offsetY+(Card.cardheight+space)*(i/collums)
            };
        }
        return new int[]{0, 0};
    }

    @Override
    public void render(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(PoseStack, mouseX, mouseY, partialTicks);
        this.renderBackground(PoseStack); //black Background
        this.renderWindow(PoseStack, offsetX, offsetY);
        renderCards(PoseStack, mouseX, mouseY);
        renderOtherStuff(PoseStack, mouseX, mouseY);
        renderHighlight(PoseStack, mouseX, mouseY);
        renderCardTooltip(PoseStack, mouseX, mouseY);
        this.font.draw(PoseStack, Component.literal("Deck builder"), offsetX+2, offsetY+2, -1);
    }

    private void renderOtherStuff(PoseStack PoseStack, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0,new ResourceLocation(""));
        this.blit(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
    }

    private void renderHighlight(PoseStack PoseStack, int mouseX, int mouseY) {
        renderCardHighlightFromList(PoseStack, mouseX, mouseY, cardSelection);
        if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)) {
            fillGradient(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
        }
    }

    private void renderCards(PoseStack PoseStack, int mouseX, int mouseY) {
        renderCardsFromList(PoseStack, deck);
        renderPartCardHighlightFromList(PoseStack, mouseX, mouseY, deck);
        renderCardsFromList(PoseStack, cardSelection, deck);
    }


    public void renderWindow(PoseStack PoseStack, int offsetX, int offsetY) {
        RenderSystem.setShaderTexture(0, oGUI);
        this.blit(PoseStack, offsetX, offsetY, 0, 0, MinecardTableImageLocations.guiwidth, MinecardTableImageLocations.guiheight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<Card> list = Card.getListOfAllNonTokenCards();
        Card partCard = getTouchingPartCardFromList(mouseX, mouseY, deck);

        if (button == 0 && partCard != null) { //left mouse
            deck.remove(partCard);
            return true;
        }

        if (button == 0 && getTouchingCard(mouseX, mouseY) != null) {
            deck.add(getTouchingCard(mouseX, mouseY).getNew());
        }

        if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)){
            onCloseOrSwitch();
            Minecraft.getInstance().setScreen(new MinecardTableGui());
            return true;
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 67 && Screen.hasControlDown()) {
            Minecraft.getInstance().keyboardHandler.setClipboard(deckString(deck));
            return true;
        } else if (keyCode == 86 && Screen.hasControlDown()) {
            deck = stringToDeck(Minecraft.getInstance().keyboardHandler.getClipboard());
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }


    public void onCloseOrSwitch() {
        String s = deckString(deck);
        ModMessages.sendToServer(new DeckC2SPacket(s, 1));
        isFirstTimeInit = true;
    }

    @Override
    public void onClose() {
        onCloseOrSwitch();
        super.onClose();
    }

    public static String deckString(List<Card> cardList) {
        StringBuilder string = new StringBuilder();
        for (Card card:cardList) {
            string.append(card.getIdString());
        }
        return string.toString();
    }

    public static Stack<Card> stringToDeck(String s) {
        Stack<Card> deck =  new Stack<>();
        StringBuilder tempString = new StringBuilder();
        List<Character> chars = s.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        int i = 0;

        for (Character character:chars) {
            tempString.append(character);
            i++;
            if ((i % 4) == 0) {
                deck.push(Card.getCardFromId(Integer.parseInt(tempString.toString())));
                i = 0;
                tempString = new StringBuilder();
            }
        }
        return deck;
    }
}