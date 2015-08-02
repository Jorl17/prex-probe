package DataLoggers;

/**
 * Created by jorl17 on 02/08/15.
 */
public class StdoutAndCsvLogger extends CompositeLogger {
    public StdoutAndCsvLogger(String csv, String separator) {
        super(new StdoutLogger(separator), new CsvLogger(csv, separator));
    }

    public StdoutAndCsvLogger(String csv) {
        this(csv, ", ");
    }
}
