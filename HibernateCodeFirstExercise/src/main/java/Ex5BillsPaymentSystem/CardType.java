package Ex5BillsPaymentSystem;

public enum CardType {
    GOLD("GOLD"),
    SILVER("SILVER"),
    PLATINUM("PLATINUM"),
    DIAMOND("DIAMOND");

    private final String value;

    CardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
