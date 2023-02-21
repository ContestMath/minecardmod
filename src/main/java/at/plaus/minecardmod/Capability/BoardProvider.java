package at.plaus.minecardmod.Capability;

import at.plaus.minecardmod.core.init.gui.Boardstate;
import at.plaus.minecardmod.core.init.gui.HalveBoardState;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BoardProvider implements ICapabilityProvider, INBTSerializable {
    public static Capability<Boardstate> PlayerBoard1 = CapabilityManager.get(new CapabilityToken<Boardstate>() { });

    private Boardstate board = null;
    private final LazyOptional<Boardstate> optional = LazyOptional.of(this::createBoardstate);

    private Boardstate createBoardstate() {
        if(this.board == null) {
            this.board = new Boardstate(new HalveBoardState(), new HalveBoardState());
        }

        return this.board;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerBoard1) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public Tag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(Tag nbt) {

    }
    /*

    @Override
    public Tag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createBoardstate().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        createBoardstate().loadNBTData(nbt);
    }

     */
}
