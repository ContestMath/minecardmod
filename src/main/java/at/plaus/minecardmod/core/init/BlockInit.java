package at.plaus.minecardmod.core.init;

import at.plaus.minecardmod.Minecardmod;
import at.plaus.minecardmod.core.init.custom.MinecardTableBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Minecardmod.MOD_ID);
    public static final RegistryObject<Block> minecard_TABLE = BLOCKS.register("minecard_table",
            ()-> new MinecardTableBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.CLAY).harvestTool(ToolType.AXE).harvestLevel(0).sound(SoundType.WOOD))); //.hardnessAndResistance(2, 2)

}
