package at.plaus.minecardmod.Capability;

import net.minecraft.nbt.CompoundTag;

public class SavedUnlockedCards {
    private String cards;


    public void copyFrom(SavedUnlockedCards source) {
        this.cards = source.cards;
    }

    public void setCards(String s) {
        this.cards = s;
    }

    public String getCards() {
        if (cards == null) {
            cards = "0004";
        }
        return this.cards;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putString("card", getCards());
    }

    public void loadNBTData(CompoundTag nbt) {
        cards = nbt.getString("card");
    }



}
