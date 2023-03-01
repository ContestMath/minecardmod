package at.plaus.minecardmod.core.init.events;

import at.plaus.minecardmod.Capability.DeckProvider;
import at.plaus.minecardmod.Capability.SavedDecks;
import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.gui.DeckBuilderGui;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.DeckSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Minecardmod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(DeckProvider.PlayerDeck0).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "tempdeck"), new DeckProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(DeckProvider.PlayerDeck0).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DeckProvider.PlayerDeck0).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }


    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SavedDecks.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(DeckProvider.PlayerDeck0).ifPresent(deck -> {
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getTempDeck()), player);
                });
            }
        }
    }

}
