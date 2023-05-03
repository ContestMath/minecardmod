package at.plaus.minecardmod.client;

public class ClientDeckData {
    private static String deck1;
    private static String deck2;
    private static String deck3;
    private static String deck4;

    public static void set1(String deck) {
        ClientDeckData.deck1 = deck;
    }

    public static String getDeck1() {
        return deck1;
    }
    public static void set2(String deck) {
        ClientDeckData.deck2 = deck;
    }

    public static String getDeck2() {
        return deck2;
    }
    public static void set3(String deck) {
        ClientDeckData.deck3 = deck;
    }

    public static String getDeck3() {
        return deck3;
    }
    public static void set4(String deck) {
        ClientDeckData.deck4 = deck;
    }

    public static String getDeck4() {
        return deck4;
    }
}
