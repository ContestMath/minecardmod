package at.plaus.minecardmod.core.init.CardGame;

import at.plaus.minecardmod.Minecardmod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BettingGui extends Screen {
    private final ResourceLocation GUI1 = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui1.png");

    private final ResourceLocation GUI2 = new ResourceLocation(Minecardmod.MOD_ID,
            "textures/gui/minecard_table_gui2.png");
    int offsetX = 0;
    int offsetY = 0;
    int x = 0;

    public BettingGui() {
        super(Component.literal("What is this string for?"));
    }
    @Override
    public void render(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks) {
        if (this.minecraft != null) {
            this.offsetX = (this.width - MinecardTableImageLocations.guiwidth) / 2;
            this.offsetY = (this.height - MinecardTableImageLocations.guiheight) / 2;
            super.render(PoseStack, mouseX, mouseY, partialTicks);
            this.renderBackground(PoseStack); //black Background
            this.renderWindow(PoseStack, offsetX, offsetY);
            int ypos = offsetY+MinecardTableImageLocations.guiheight/2;
            this.font.draw(PoseStack, Component.literal("Increase your emerald bet with the arrow keys and accept with space"), offsetX+2, ypos, -1);
            this.font.draw(PoseStack, Component.literal("(shift and ctrl for bigger step sizes)"), offsetX+2, ypos + font.lineHeight, -1);
            this.font.draw(PoseStack, Component.literal("You are currently betting: " + Integer.toString(x) + " emeralds"), offsetX+2, ypos + (font.lineHeight * 2), -1);
        }
    }

    private void renderWindow(PoseStack PoseStack, int offsetX, int offsetY) {
        RenderSystem.setShaderTexture(0, GUI1);
        this.blit(PoseStack, offsetX, offsetY, 0, 0, 256, MinecardTableImageLocations.guiheight);
        RenderSystem.setShaderTexture(0,GUI2);
        this.blit(PoseStack, offsetX+256, offsetY, 0, 0, MinecardTableImageLocations.guiwidth-256, MinecardTableImageLocations.guiheight);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) {
            int y = 0;
            int tempX = x;
            for (ItemStack item:Minecraft.getInstance().player.getInventory().items) {
                if (item.getItem().equals(Items.EMERALD)) {
                    y = item.getCount();
                    item.shrink(tempX);
                    tempX -= y;
                }
            }
            Minecraft.getInstance().setScreen(new MinecardTableGui(x));
            return true;
        } else if (keyCode == 262) {
            x +=1;
            if (Screen.hasShiftDown()) {
                x += 9;
            } else if (Screen.hasControlDown()) {
                x += 99;
            }
            if (Minecraft.getInstance().player.getInventory().countItem(Items.EMERALD) < x) {
                x = Minecraft.getInstance().player.getInventory().countItem(Items.EMERALD);
            }
        } else if (keyCode == 263) {
            x -= 1;
            if (Screen.hasShiftDown()) {
                x -= 9;
            } else if (Screen.hasControlDown()) {
                x -= 99;
            }
            if (x < 0) {
                x = 0;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
