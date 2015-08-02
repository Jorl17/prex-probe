import BuildingBlocks.DataPoint;
import DataLoggers.CsvLogger;
import DataLoggers.DataLogger;
import DataLoggers.StdoutAndCsvLogger;
import DataSamplers.*;
import Features.Feature;
import Util.Utils;
import org.hyperic.sigar.Sigar;

import java.util.ArrayList;

/**
 * Created by jorl17 on 02/08/15.
 */
public class MiniPrexMonitor {
    private final String outCsv;
    private final boolean logToStdout;
    private final int samplingPeriod;

    private DataLogger logger;
    private MultiFeatureSampler sampler;

    private static final String SEPARATOR = ", ";
    private static final int DEFAULT_SAMPLING_PERIOD = 100;

    public MiniPrexMonitor(String outCsv, boolean logToStdout, int samplingPeriod) {
        this.outCsv = outCsv;
        this.logToStdout = logToStdout;
        this.samplingPeriod = samplingPeriod;
    }

    public MiniPrexMonitor(String outCsv, boolean logToStdout) {
        this(outCsv,logToStdout, DEFAULT_SAMPLING_PERIOD);
    }

    private void prepareLogger() {
        this.logger = logToStdout ? new StdoutAndCsvLogger(outCsv, SEPARATOR) : new CsvLogger(outCsv, SEPARATOR);
        Runtime.getRuntime().addShutdownHook(new Thread(logger::cleanup));
    }

    private void prepareSampler() {
        Sigar sigar = new Sigar();
        sampler = new MultiFeatureSampler(sigar, new CPUSampler(sigar), new MemorySampler(sigar), new FileSystemSampler(sigar), new TCPSampler(sigar), new NetIfaceSampler(sigar));
    }

    private void logHeader() {
        sampler.sample();
        logger.logHeader(new DataPoint(sampler.getProvidedFeatures()));
    }

    private void sampleAndLog() {
        sampler.sample();
        logger.log(new DataPoint(sampler.getProvidedFeatures()));
    }

    public void main() {
        prepareSampler();
        prepareLogger();
        logHeader();

        for(;;) {
            sampleAndLog();
            Utils.sleepNoInterrupt(samplingPeriod);
        }
    }
}
