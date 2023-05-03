package at.plaus.minecardmod.Capability;

import net.minecraft.nbt.CompoundTag;

public class SavedDeck {
    private String Deck;


    public void copyFrom(SavedDeck source) {
        this.Deck = source.Deck;
    }

    public void setDeck(String s) {
        this.Deck = s;
    }

    public String getDeck() {
        if (Deck == null) {
            Deck = "0004";
        }
        return this.Deck;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("deck", getDeck());
    }

    public void loadNBTData(CompoundTag nbt) {
        Deck = nbt.getString("deck");
    }



}
