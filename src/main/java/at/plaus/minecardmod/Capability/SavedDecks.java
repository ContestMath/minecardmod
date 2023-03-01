package at.plaus.minecardmod.Capability;

import net.minecraft.nbt.CompoundTag;

public class SavedDecks {
    private String tempDeck;

    public void copyFrom(SavedDecks source) {
        this.tempDeck = source.tempDeck;
    }

    public void setTempDeck(String s) {
        this.tempDeck = s;
    }

    public String getTempDeck() {
        if (tempDeck == null) {
            tempDeck = "0004";
        }
        return this.tempDeck;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("tempdeck", getTempDeck());
    }

    public void loadNBTData(CompoundTag nbt) {
        tempDeck = nbt.getString("tempdeck");
    }



}
