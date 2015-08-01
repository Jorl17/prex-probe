package DataLoggers;

import BuildingBlocks.DataPoint;
import Util.Utils;

/**
 * Created by jorl17 on 02/08/15.
 */
public class StdoutLogger implements DataLogger {
    public void logHeader(DataPoint point) {
        System.out.println(Utils.join(", ", point.getHeaders()));
    }
    public void log(DataPoint point) {
        System.out.println(point);
    }
}
