package at.plaus.minecardmod.core.init.gui;

import at.plaus.minecardmod.Capability.DeckProvider;
import at.plaus.minecardmod.Capability.SavedDecks;
import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.GlobalValues;
import at.plaus.minecardmod.core.init.menu.MinecardScreenMenu;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.DeckC2SPacket;
import at.plaus.minecardmod.networking.packet.DeckSyncS2CPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

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
    public static final Component name = Component.translatable("tooltip.minecardmod.minecard_table");
    public final MinecardScreenMenu menu;
    public final Inventory inv;
    public final Component component;

    private static final ResourceLocation GUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui.png");
    private static final ResourceLocation oGUI = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/untitled.png");

    public DeckBuilderGui(MinecardScreenMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.inv = inventory;
        this.menu = menu;
        this.component = component;
    }

    @Override
    public int[] getCardPosInList(int i, List<Card> list) {
        int space = 5;
        if (list.equals(deck)) {
            return new int[] {
                    MinecardTableImageLocations.guiwidth+offsetX- Card.cardwidth-10,
                    offsetY+10+i*cardOffset
            };
        } else if (rows*collums*page <= i && i < rows*collums*(page+1)) {
            return new int[] {
                    10+offsetX+(Card.cardwidth+space)*(i%collums),
                    15+offsetY+(Card.cardheight+space)*(i/collums)
            };
        }

        //Minecraft.getInstance().player.sendChatMessage(list.toString());
        return new int[]{0, 0};
    }

    @Override
    public void render(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(PoseStack, mouseX, mouseY, partialTicks);
        this.renderBackground(PoseStack); //black Background
        this.renderWindow(PoseStack, offsetX, offsetY);
        renderCards(PoseStack, mouseX, mouseY);
        renderDeck(PoseStack);
        renderOtherStuff(PoseStack, mouseX, mouseY);
        renderHighlight(PoseStack, mouseX, mouseY);
        renderCardTooltip(PoseStack, Card.getListOfAllNonTokenCards(), mouseX, mouseY);
        this.font.draw(PoseStack, Component.literal("Deck builder"), offsetX+2, offsetY+2, -1);
    }

    private void renderOtherStuff(PoseStack PoseStack, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0,new ResourceLocation(""));
        this.blit(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }

    private void renderHighlight(PoseStack PoseStack, int mouseX, int mouseY) {
        renderCardHighlightFromList(PoseStack, mouseX, mouseY, Card.getListOfAllNonTokenCards());
        if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)) {
            fillGradient(PoseStack, offsetX+MinecardTableImageLocations.changeX, offsetY+MinecardTableImageLocations.changeY, 0, 0, MinecardTableImageLocations.changeWidth, MinecardTableImageLocations.changeHeight);
        }
    }

    private void renderCards(PoseStack PoseStack, int mouseX, int mouseY) {
        renderCardsFromList(PoseStack, deck);
        renderPartCardHighlightFromList(PoseStack, mouseX, mouseY, deck);
        renderCardsFromList(PoseStack, Card.getListOfAllNonTokenCards());
    }

    private void renderDeck(PoseStack PoseStack) {
        renderCardsFromList(PoseStack, Card.getListOfAllNonTokenCards());
    }


    public void renderWindow(PoseStack PoseStack, int offsetX, int offsetY) {
        RenderSystem.setShaderTexture(0, oGUI);
        this.blit(PoseStack, offsetX, offsetY, 0, 0, MinecardTableImageLocations.guiwidth, MinecardTableImageLocations.guiheight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<Card> list = Card.getListOfAllNonTokenCards();
        int cardindex = getTouchingCardFromList(mouseX, mouseY, list);
        if (button == 0 && cardindex != -1) { //left mouse
            deck.add(list.get(cardindex));
        }

        cardindex = getTouchingPartCardFromList(mouseX, mouseY, deck);
        if (button == 0 && cardindex != -1) { //left mouse
            deck.remove(deck.get(cardindex));
        }
        if (isWithinBoundingBox(mouseX, mouseY, offsetX+MinecardTableImageLocations.changeX, offsetX+MinecardTableImageLocations.changeX+MinecardTableImageLocations.changeWidth, offsetY+MinecardTableImageLocations.changeY, offsetY+MinecardTableImageLocations.changeY+MinecardTableImageLocations.changeHeight)){
            onCloseOrSwitch();
            Minecraft.getInstance().setScreen(new MinecardTableGui(menu, inv, component));
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public void onCloseOrSwitch() {
        String s = deckString(deck);
        ModMessages.sendToServer(new DeckC2SPacket(s));
    }

    @Override
    public void onClose() {
        onCloseOrSwitch();
        super.onClose();
    }

    public static String deckString(List<Card> cardList) {
        StringBuilder string = new StringBuilder();
        for (Card card:cardList) {
            string.append(Card.getIdStringFromCardName(card.getNameFromCard()));
        }
        return string.toString();
    }

    public static Stack<Card> stringToDeck(String s) {
        Stack<Card> deck =  new Stack<>();
        List<Character> chars = s.chars().mapToObj(e->(char)e).collect(Collectors.toList());
        StringBuilder tempString = new StringBuilder();
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

    @Override
    public Component getDisplayName() {
        return name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
        return null;
    }
}
