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
    public static Capability<SavedDeck> PlayerDeck1 = CapabilityManager.get(new CapabilityToken<SavedDeck>() { });
    public static Capability<SavedDeck> PlayerDeck2 = CapabilityManager.get(new CapabilityToken<SavedDeck>() { });
    public static Capability<SavedDeck> PlayerDeck3 = CapabilityManager.get(new CapabilityToken<SavedDeck>() { });
    public static Capability<SavedDeck> PlayerDeck4 = CapabilityManager.get(new CapabilityToken<SavedDeck>() { });

    private SavedDeck savedDecks = null;
    private final LazyOptional<SavedDeck> optional = LazyOptional.of(this::createSavedBoards);

    private SavedDeck createSavedBoards() {
        if(this.savedDecks == null) {
            this.savedDecks = new SavedDeck();
        }

        return this.savedDecks;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerDeck1) {
            return optional.cast();
        }
        if(cap == PlayerDeck2) {
            return optional.cast();
        }
        if(cap == PlayerDeck3) {
            return optional.cast();
        }
        if(cap == PlayerDeck4) {
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
