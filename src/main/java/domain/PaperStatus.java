package domain;

public enum PaperStatus {
    Accepted,
    Rejected,
    InReview;

    int ordinal = 0;

    public static PaperStatus byOrdinal(int ord) {
        for (PaperStatus m : PaperStatus.values()) {
            if (m.ordinal== ord) {
                return m;
            }
        }
        return null;
    }
}
