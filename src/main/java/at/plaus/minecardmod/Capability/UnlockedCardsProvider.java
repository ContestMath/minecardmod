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

public class UnlockedCardsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<SavedUnlockedCards> PlayerUnlockedCards = CapabilityManager.get(new CapabilityToken<SavedUnlockedCards>() { });

    private SavedUnlockedCards savedUnlockedCards = null;
    private final LazyOptional<SavedUnlockedCards> optional = LazyOptional.of(this::createUnlockedCards);

    private SavedUnlockedCards createUnlockedCards() {
        if(this.savedUnlockedCards == null) {
            this.savedUnlockedCards = new SavedUnlockedCards();
        }

        return this.savedUnlockedCards;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PlayerUnlockedCards) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createUnlockedCards().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createUnlockedCards().loadNBTData(nbt);
    }
}
