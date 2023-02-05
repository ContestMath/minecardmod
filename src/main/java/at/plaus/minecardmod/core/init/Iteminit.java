package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.custom.empty_minecard_card;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Iteminit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Minecardmod.MOD_ID);
    public static final RegistryObject<Item> EMPTY_MINECARD_CARD = ITEMS.register("empty_minecard_card",
            ()-> new empty_minecard_card(new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
}
