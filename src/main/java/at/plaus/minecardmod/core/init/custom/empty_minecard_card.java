package at.plaus.minecardmod.core.init.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class empty_minecard_card extends Item {
    public empty_minecard_card(Properties properties) {super(properties);}

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> tooltip, ITooltipFlag p_77624_4_) {
        tooltip.add(new TranslationTextComponent("tooltip.minecardmod.empty_minecard_card"));
        super.appendHoverText(p_77624_1_, p_77624_2_, tooltip, p_77624_4_);
    }
}


