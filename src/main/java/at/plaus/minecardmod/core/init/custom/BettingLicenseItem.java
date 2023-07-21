package at.plaus.minecardmod.core.init.custom;

import at.plaus.minecardmod.Capability.SavedUnlockedCards;
import at.plaus.minecardmod.core.init.gui.Card;
import at.plaus.minecardmod.core.init.gui.MinecardTableGui;
import at.plaus.minecardmod.tileentity.MinecardTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BettingLicenseItem extends Item {
    public BettingLicenseItem(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> tooltip, TooltipFlag p_77624_4_) {
        tooltip.add(Component.translatable("tooltip.minecardmod.betting_license"));
        super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return super.use(level, player, hand);
    }
}


