package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.custom.empty_minecard_card;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Iteminit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Minecardmod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final RegistryObject<Item> EMPTY_MINECARD_CARD = ITEMS.register("empty_minecard_card",
            ()-> new empty_minecard_card(new Item.Properties()));
}
