package at.plaus.minecardmod.core.init.custom;
import at.plaus.minecardmod.core.init.gui.DeckBuilderGui;
import at.plaus.minecardmod.core.init.gui.MinecardTableGui;
import at.plaus.minecardmod.tileentity.MinecardTableTile;
import at.plaus.minecardmod.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;


import javax.annotation.Nullable;

public class MinecardTableBlock extends Block {
    public MinecardTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World worldIn, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if(!worldIn.isClientSide()) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(!player.isCrouching()) {
                if(tileEntity instanceof MinecardTableTile) {
                    Minecraft.getInstance().setScreen(new MinecardTableGui());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }
        return super.use(p_225533_1_, worldIn, pos, player, p_225533_5_, p_225533_6_);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.minecard_TABLE_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
