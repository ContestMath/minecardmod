package at.plaus.minecardmod.Capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class DeckProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<SavedDecks> PlayerDeck0 = CapabilityManager.get(new CapabilityToken<SavedDecks>() { });

    private SavedDecks savedDecks = null;
    private final LazyOptional<SavedDecks> optional = LazyOptional.of(this::createSavedBoards);

    private SavedDecks createSavedBoards() {
        if(this.savedDecks == null) {
            this.savedDecks = new SavedDecks();
        }

        return this.savedDecks;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerDeck0) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createSavedBoards().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createSavedBoards().loadNBTData(nbt);
    }


}
