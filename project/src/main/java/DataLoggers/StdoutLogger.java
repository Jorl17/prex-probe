package DataLoggers;

import BuildingBlocks.DataPoint;
import Util.Utils;

/**
 * Created by jorl17 on 02/08/15.
 */
public class StdoutLogger extends SeparatorLoggerAdapter {
    public StdoutLogger(String separator) {
        super(separator);
    }

    public StdoutLogger() {
        super(", ");
    }

    @Override
    public void logHeader(DataPoint point) {
        System.out.println("Timestamp" + separator + Utils.join(separator, point.getHeaders()));
    }

    @Override
    public void log(DataPoint point) {
        System.out.println(point.getFeatureString(separator));
    }

    @Override
    public void cleanup() {
    }
}
