package at.plaus.minecardmod.tileentity;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.BlockInit;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Minecardmod.MOD_ID);

    public static RegistryObject<TileEntityType<MinecardTableTile>> minecard_TABLE_TILE =
            TILE_ENTITIES.register("minecard_table_tile", () -> TileEntityType.Builder.of(
                    MinecardTableTile::new, BlockInit.minecard_TABLE.get()).build(null)
            );

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
