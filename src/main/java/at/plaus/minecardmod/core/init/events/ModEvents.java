package at.plaus.minecardmod.core.init.events;

import at.plaus.minecardmod.Capability.DeckProvider;
import at.plaus.minecardmod.Capability.SavedDeck;
import at.plaus.minecardmod.Capability.SavedUnlockedCards;
import at.plaus.minecardmod.Capability.UnlockedCardsProvider;
import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.BlockInit;
import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import at.plaus.minecardmod.core.init.Iteminit;
import at.plaus.minecardmod.core.init.villager.Villagers;
import at.plaus.minecardmod.networking.ModMessages;
import at.plaus.minecardmod.networking.packet.DeckSyncS2CPacket;
import at.plaus.minecardmod.networking.packet.OpenScreenS2CPacket;
import at.plaus.minecardmod.networking.packet.UnlockedCardsSyncS2CPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static org.openjdk.nashorn.internal.codegen.types.Type.isAssignableFrom;

@Mod.EventBusSubscriber(modid = Minecardmod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(DeckProvider.PlayerDeck1).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "deck1"), new DeckProvider());
            }
            if(!event.getObject().getCapability(DeckProvider.PlayerDeck2).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "deck2"), new DeckProvider());
            }
            if(!event.getObject().getCapability(DeckProvider.PlayerDeck3).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "deck3"), new DeckProvider());
            }
            if(!event.getObject().getCapability(DeckProvider.PlayerDeck4).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "deck4"), new DeckProvider());
            }
            if(!event.getObject().getCapability(UnlockedCardsProvider.PlayerUnlockedCards).isPresent()) {
                event.addCapability(new ResourceLocation(Minecardmod.MOD_ID, "cards"), new UnlockedCardsProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(DeckProvider.PlayerDeck1).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DeckProvider.PlayerDeck1).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(DeckProvider.PlayerDeck2).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DeckProvider.PlayerDeck2).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(DeckProvider.PlayerDeck3).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DeckProvider.PlayerDeck3).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(DeckProvider.PlayerDeck4).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DeckProvider.PlayerDeck4).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().getCapability(UnlockedCardsProvider.PlayerUnlockedCards).ifPresent(oldStore -> {
                event.getOriginal().getCapability(UnlockedCardsProvider.PlayerUnlockedCards).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }


    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SavedDeck.class);
        event.register(SavedUnlockedCards.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(UnlockedCardsProvider.PlayerUnlockedCards).ifPresent(cards -> {
                    ModMessages.sendToPlayer(new UnlockedCardsSyncS2CPacket(cards.getCards()), player);
                });
                player.getCapability(DeckProvider.PlayerDeck1).ifPresent(deck -> {
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 1), player);
                });
                player.getCapability(DeckProvider.PlayerDeck2).ifPresent(deck -> {
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 2), player);
                });
                player.getCapability(DeckProvider.PlayerDeck3).ifPresent(deck -> {
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 3), player);
                });
                player.getCapability(DeckProvider.PlayerDeck4).ifPresent(deck -> {
                    ModMessages.sendToPlayer(new DeckSyncS2CPacket(deck.getDeck(), 4), player);
                });
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == Villagers.CARD_TRADER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(Iteminit.MINECARD_PACK_ITEM.get(), 1);
            ItemStack stack2 = new ItemStack(Iteminit.BETTING_LICENSE.get(), 1);
            int villagerLevel = 1;

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    stack,10,8,0.02F));

            trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    stack2,10,8,0.02F));
        }
    }

    @SubscribeEvent
    public static void addInteraction(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer && event.getTarget() instanceof Villager && player.getItemInHand(event.getHand()).getItem().equals(Iteminit.BETTING_LICENSE.get()) && player.isCrouching() && !event.getLevel().isClientSide()) {
            ModMessages.sendToPlayer(new OpenScreenS2CPacket(5), (ServerPlayer) player);
        }
    }
}
