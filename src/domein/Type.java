package domein;

public enum Type {
    MAN('M'),
    MUUR('#'),
    KIST('x'),
    DOEL('.'),
    LEEG(' '),
    KISTDOEL('S'),
    MANDOEL('D');

    private Character teken;

    private Type(Character teken) {
        this.teken = teken;
    }

    public Character getTeken() {
        return this.teken;
    }
}
