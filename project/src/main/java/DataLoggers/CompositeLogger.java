package DataLoggers;

import BuildingBlocks.DataPoint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jorl17 on 02/08/15.
 */
public class CompositeLogger implements DataLogger {
    private ArrayList<DataLogger> loggers;

    public CompositeLogger(ArrayList<DataLogger> loggers) {
        this.loggers = loggers;
    }

    public CompositeLogger(DataLogger... loggers) {
        this.loggers = new ArrayList<>();
        Collections.addAll(this.loggers, loggers);
    }

    @Override
    public void log(DataPoint point) {
        for ( DataLogger l : loggers )
            l.log(point);
    }

    @Override
    public void logHeader(DataPoint point) {
        for ( DataLogger l : loggers )
            l.logHeader(point);
    }

    @Override
    public void cleanup() {
        for ( DataLogger l : loggers )
            l.cleanup();
    }
}
