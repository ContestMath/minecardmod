package at.plaus.minecardmod.core.init.item;
import at.plaus.minecardmod.core.init.CardGame.MinecardTableGui;
import at.plaus.minecardmod.core.init.HideClientside;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MinecardTableBlock extends Block {
    public MinecardTableBlock(Properties properties) {
        super(properties);
    }

    
    @Override
    public InteractionResult use(BlockState p_225533_1_, Level level, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult blockHitResult) {
        if(level.isClientSide() && !player.isCrouching()) {
            HideClientside.openDeckBuilderScreen();
        }
        return super.use(p_225533_1_, level, pos, player, p_225533_5_, blockHitResult);
    }



    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }


}
