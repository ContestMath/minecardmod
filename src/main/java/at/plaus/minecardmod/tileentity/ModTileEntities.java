package at.plaus.minecardmod.tileentity;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.BlockInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static DeferredRegister<BlockEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Minecardmod.MOD_ID);

    public static RegistryObject<BlockEntityType<MinecardTableBlockEntity>> minecard_TABLE_TILE =
            TILE_ENTITIES.register("minecard_table_tile", () -> BlockEntityType.Builder.of(MinecardTableBlockEntity::new,
                    BlockInit.minecard_TABLE.get()).build(null));



    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
