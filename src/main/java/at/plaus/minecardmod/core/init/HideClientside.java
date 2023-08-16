package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import net.minecraft.client.Minecraft;

public class HideClientside {
    public static void openMinecardScreen () {
        Minecraft.getInstance().setScreen(new MinecardTableGui());
    }
}
