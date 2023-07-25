package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.item.BettingLicenseItem;
import at.plaus.minecardmod.core.init.item.MinecardPackItem;
import at.plaus.minecardmod.core.init.item.empty_minecard_card;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

    public static final RegistryObject<Item> MINECARD_PACK_ITEM = ITEMS.register("minecard_pack",
            ()-> new MinecardPackItem(new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<Item> BETTING_LICENSE = ITEMS.register("betting_license",
            ()-> new BettingLicenseItem(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1)));
}
