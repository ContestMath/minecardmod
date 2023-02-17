package at.plaus.minecardmod.tileentity;

import at.plaus.minecardmod.core.init.menu.MinecardScreenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinecardTableBlockEntity extends BlockEntity implements MenuProvider {

    protected final ContainerData data;

    public MinecardTableBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.minecard_TABLE_TILE.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Minecard Table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player p_39956_) {
        return new MinecardScreenMenu(id, inventory, this, this.data);
    }
}
