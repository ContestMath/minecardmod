package at.plaus.minecardmod.core.init.item;

import at.plaus.minecardmod.Capability.SavedUnlockedCards;
import at.plaus.minecardmod.core.init.CardGame.Card;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class MinecardPackItem extends Item {
    public static final int numberOfCardsGained = 5;
    public MinecardPackItem(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> tooltip, TooltipFlag p_77624_4_) {
        tooltip.add(Component.translatable("tooltip.minecardmod.minecard_pack_card"));
        super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            Random rand = new Random();
            for (int i=0; i<numberOfCardsGained; i++) {
                SavedUnlockedCards.unlock((Card.getListOfAllNonTokenCards().get(rand.nextInt(Card.getListOfAllNonTokenCards().size())).getClass()));
            }
            player.getItemInHand(hand).shrink(1);
        }
        return super.use(level, player, hand);
    }
}


