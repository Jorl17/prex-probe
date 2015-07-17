import java.sql.Timestamp;
import java.util.Date;

public class DataTimestamp {
    private final Date date;
    private final boolean unixPresentationModeByDefault;

    public DataTimestamp(boolean unixPresentationModeByDefault) {
        this.date= new java.util.Date();
        this.unixPresentationModeByDefault = unixPresentationModeByDefault;
    }
    public DataTimestamp() {
        this(false);
    }

    public String asUnixTime() {
        return "" + this.date.getTime();
    }

    public String asReadableTimestamp() {
        return "" + new Timestamp(this.date.getTime());
    }

    public String toString()  {
        if (unixPresentationModeByDefault)
            return asUnixTime();
        else
            return asReadableTimestamp();
    }
}
