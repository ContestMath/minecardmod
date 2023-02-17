package at.plaus.minecardmod.core.init.custom;
import at.plaus.minecardmod.tileentity.MinecardTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class MinecardTableBlock extends BaseEntityBlock {
    public MinecardTableBlock(Properties properties) {
        super(properties);
    }

    
    @Override
    public InteractionResult use(BlockState p_225533_1_, Level level, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult blockHitResult) {
        if(!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(blockHitResult.getBlockPos());

            if(!player.isCrouching()) {
                if(blockEntity instanceof MinecardTableBlockEntity && player instanceof ServerPlayer) {
                    NetworkHooks.openScreen((ServerPlayer) player, (MinecardTableBlockEntity) blockEntity, pos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }
        return super.use(p_225533_1_, level, pos, player, p_225533_5_, blockHitResult);
    }



    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MinecardTableBlockEntity(blockPos, blockState);
    }

}
