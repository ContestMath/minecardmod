package at.plaus.minecardmod;

import at.plaus.minecardmod.core.init.BlockInit;
import at.plaus.minecardmod.core.init.Iteminit;
import at.plaus.minecardmod.core.init.loot.ModLootModifiers;
import at.plaus.minecardmod.core.init.villager.Villagers;
import at.plaus.minecardmod.networking.ModMessages;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

        BlockInit.register(bus);
        Iteminit.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLootModifiers.register(bus);
        Villagers.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerTabs);
    }



    public static CreativeModeTab MY_TAB;
    private void registerTabs(CreativeModeTabEvent.Register event)
    {
        MY_TAB = event.registerCreativeModeTab(new ResourceLocation(Minecardmod.MOD_ID, "main_tab"), builder -> builder
                .icon(() -> new ItemStack(Iteminit.MINECARD_PACK_ITEM.get()))
                .title(Component.translatable("tabs.modid.main_tab"))
                .displayItems((featureFlags, output, hasOp) -> {
                    output.accept(Iteminit.MINECARD_PACK_ITEM.get());
                    output.accept(Iteminit.EMPTY_MINECARD_CARD.get());
                    output.accept(BlockInit.MINECARD_TABLE.get());
                    output.accept(Iteminit.BETTING_LICENSE.get());
                })
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Villagers.registerPOIs();
        });
        ModMessages.register();
    }
}

