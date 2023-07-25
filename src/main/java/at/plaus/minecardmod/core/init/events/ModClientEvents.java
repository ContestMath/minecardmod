package at.plaus.minecardmod.core.init.events;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.CardGame.Card;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.UnlockedCardsC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = Minecardmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {
    public static Card eventCard;


    @SubscribeEvent
    public static void gatherComponents(RenderTooltipEvent.GatherComponents event) {
        if (eventCard != null) {
            event.getTooltipElements().add(Either.right(new CardTooltipComponent(eventCard)));
            for (Component tooltipElement:eventCard.getTooltip2()) {
                event.getTooltipElements().add(Either.left(tooltipElement));
            }
        }

    }

    @SubscribeEvent
    public static void onRegisterCommands (RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("clearCards").requires((x) -> {
            return x.hasPermission(0);
        }
        ).executes((p_136721_) -> {
            return clearCards();
        }));
    }

    private static int clearCards() {
            ModMessages.sendToServer(new UnlockedCardsC2SPacket(""));
            return 0;
    }


    @Mod.EventBusSubscriber(modid = Minecardmod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void register(RegisterClientTooltipComponentFactoriesEvent event)
        {
            event.register(CardTooltipComponent.class, ClientCardTooltipComponent::new);
        }
    }

    //The rest of the code (and some before this) is stolen from Gigaherz on the modding discord
    public static record CardTooltipComponent(Card card) implements TooltipComponent
    {
    }

    public static class ClientCardTooltipComponent implements net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent {

        private final ResourceLocation texture;

        private final Card card;
        private final Component label1;
        private final Component label2;
        public static final ResourceLocation frame = new ResourceLocation(Minecardmod.MOD_ID, "textures/gui/frame.png");



        public ClientCardTooltipComponent(CardTooltipComponent component)
        {
            this.card = component.card();
            List<String> label1List = new ArrayList<>();
            List<String> label2List = new ArrayList<>();
            for (Component component1:card.getTooltip1()) {
                label1List.add(component1.getString());
            }
            for (Component component1:card.getTooltip2()) {
                label2List.add(component1.getString());
            }
            String label1String = label1List.stream().max(Comparator.comparingInt(String::length)).get();
            String label2String = label2List.stream().max(Comparator.comparingInt(String::length)).get();

            this.label1 = Component.literal(label1String);
            this.label2 = Component.literal(label2String);
            this.texture = card.getTexture();
        }

        @Override
        public int getHeight()
        {
            return Card.bigCardheight + 10; // 20 + Font.lineHeight * 2
        }

        @Override
        public int getWidth(Font font)
        {
            return Math.max(Card.bigCwidth, Math.max(font.width(label1), font.width(label2)));
        }

        @Override
        public void renderImage(Font font, int x, int y, PoseStack poseStack, ItemRenderer itemRenderer, int p_194053_)
        {

            poseStack.pushPose();
            poseStack.translate(0, 0, 300);

            int xCardPos = x+(getWidth(font) - Card.bigCwidth)/2;



            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            RenderSystem.setShaderTexture(0, texture);
            GuiComponent.blit(poseStack, xCardPos, y+5, 0, (float)0, (float)0, Card.bigCwidth, Card.bigCardheight, 256, 256);

            RenderSystem.setShaderTexture(0, frame);
            GuiComponent.blit(poseStack, xCardPos, y+5, 0, (float)20, (float)20, Card.bigCwidth, Card.bigCardheight, 256, 256);
            font.drawShadow(poseStack, Integer.toString(card.strength), xCardPos+getWidth(font)-8, y-5+Card.bigCardheight, 0xFFFFFF);


            poseStack.popPose();
        }
    }


}
