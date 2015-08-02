package DataLoggers;

/**
 * Created by jorl17 on 02/08/15.
 */
public abstract class SeparatorLoggerAdapter implements DataLogger {
    protected String separator;
    public SeparatorLoggerAdapter(String sep) {
        this.separator = sep;
    }
    public SeparatorLoggerAdapter() {
        this(", ");
    }
}
