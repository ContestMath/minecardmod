package at.plaus.minecardmod;

import at.plaus.minecardmod.core.init.BlockInit;
import at.plaus.minecardmod.core.init.Iteminit;
import at.plaus.minecardmod.tileentity.ModTileEntities;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("minecardmod")
@Mod.EventBusSubscriber(modid = Minecardmod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class Minecardmod {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "minecardmod";

    public Minecardmod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(bus);
        Iteminit.ITEMS.register(bus);
        ModTileEntities.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegigisterItems(final RegistryEvent.Register<Item> event) {
        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach((block -> {
            event.getRegistry().register(new BlockItem(block, new Item.Properties().tab(ItemGroup.TAB_COMBAT)).setRegistryName(block.getRegistryName()));
        }));
    }
}
