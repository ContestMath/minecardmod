package at.plaus.minecardmod.core.init.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.property.Properties;

import javax.annotation.Nullable;
import java.util.List;

public class empty_minecard_card extends Item {
    public empty_minecard_card(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> tooltip, TooltipFlag p_77624_4_) {
        tooltip.add(Component.translatable("tooltip.minecardmod.empty_minecard_card"));
        super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
    }


}


