package at.plaus.minecardmod.client;

public class ClientDeckData {
    private static String deck;

    public static void set(String deck) {
        ClientDeckData.deck = deck;
    }

    public static String getDeck() {
        return deck;
    }
}
