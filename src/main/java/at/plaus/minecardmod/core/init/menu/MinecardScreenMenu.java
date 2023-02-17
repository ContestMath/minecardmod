package at.plaus.minecardmod.core.init.menu;

import at.plaus.minecardmod.tileentity.MinecardTableBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MinecardScreenMenu extends AbstractContainerMenu {

    public final MinecardTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;


    public MinecardScreenMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, inventory.player.level.getBlockEntity(friendlyByteBuf.readBlockPos()), new SimpleContainerData(2));
    }

    public MinecardScreenMenu(int p_38852_, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(MenuTypeInit.MINECARD_TABLE_MENU.get(), p_38852_);
        blockEntity = (MinecardTableBlockEntity) entity;
        this.level = inventory.player.level;
        this.data = data;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }
}
