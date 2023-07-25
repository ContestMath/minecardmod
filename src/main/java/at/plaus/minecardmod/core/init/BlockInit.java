package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.item.MinecardTableBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Minecardmod.MOD_ID);

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return Iteminit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> teReturn = BLOCKS.register(name, block);
        registerBlockItem(name, teReturn);
        return teReturn;
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final RegistryObject<Block> MINECARD_TABLE = registerBlock("minecard_table",
            ()-> new MinecardTableBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.CLAY).sound(SoundType.WOOD))); //.hardnessAndResistance(2, 2)

}
