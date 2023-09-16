package at.plaus.minecardmod.core.init.CardGame;

public class OptionsCard extends Card {

    public OptionsCard(String texture, String[] tooltip, String name) {
        super(
                0,
                texture,
                CardTypes.EFFECT,
                tooltip,
                name
        );
        isToken = true;
    }

}
