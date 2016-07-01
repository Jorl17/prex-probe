package DataLoggers;

import BuildingBlocks.DataPoint;
import Features.Feature;
import prex.client.PrexClient;
import prex.client.Probe;
import prex.common.Sample;
import prex.common.TimestampUnit;

import java.io.IOException;


public class PreXProbeLogger implements DataLogger {
    private PrexClient probe;

    public PreXProbeLogger(String src, String host, int port) throws IOException {
        this.probe = new PrexClient(src, host, port, 100);
    }

    @Override
    public void log(DataPoint point) {
        for (Feature f : point.getFeatures())
            probe.sample(f.getFeatureName(), f.asFloat());
    }

    @Override
    public void logHeader(DataPoint point) {
        // Not needed. Features are sent with their names attached
    }

    @Override
    public void cleanup() {
        probe.close();
    }
}
