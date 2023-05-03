package at.plaus.minecardmod.client;

public class ClientCardData {
    private static String cards;

    public static void set(String cards) {
        ClientCardData.cards = cards;
    }

    public static String getDeck() {
        return cards;
    }
}
