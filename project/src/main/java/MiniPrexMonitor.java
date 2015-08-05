import BuildingBlocks.DataPoint;
import DataLoggers.CsvLogger;
import DataLoggers.DataLogger;
import DataLoggers.StdoutAndCsvLogger;
import DataSamplers.*;
import ExecutionManagers.ExecutionManager;
import ExecutionManagers.ManagedClass;
import Features.Feature;
import Util.Utils;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jorl17 on 02/08/15.
 */
public class MiniPrexMonitor implements ManagedClass {
    private final String outCsv;
    private final boolean logToStdout;
    private final int samplingPeriod;

    private DataLogger logger;
    private MultiFeatureSampler sampler;

    private static final String SEPARATOR = ", ";
    private static final int DEFAULT_SAMPLING_PERIOD = 100;

    private AtomicBoolean shouldStop;

    public MiniPrexMonitor(String outCsv, boolean logToStdout, int samplingPeriod) {
        this.outCsv = outCsv;
        this.logToStdout = logToStdout;
        this.samplingPeriod = samplingPeriod;
        this.shouldStop = new AtomicBoolean(false);
    }

    public MiniPrexMonitor(String outCsv, boolean logToStdout) {
        this(outCsv,logToStdout, DEFAULT_SAMPLING_PERIOD);
    }

    private void prepareLogger() {
        this.logger = logToStdout ? new StdoutAndCsvLogger(outCsv, SEPARATOR) : new CsvLogger(outCsv, SEPARATOR);
        Runtime.getRuntime().addShutdownHook(new Thread(logger::cleanup));
    }

    private void prepareSampler() {
        Sigar sigar;
        try {
          sigar = new Sigar();
        } catch (Exception e) {
            tryUnpackLibraries();
            sigar = new Sigar();
        }
        sampler = new MultiFeatureSampler(sigar, new CPUSampler(sigar), new MemorySampler(sigar), new FileSystemSampler(sigar), new TCPSampler(sigar), new NetIfaceSampler(sigar));
    }

    private void tryUnpackLibraries() {
        //FIXME

    }

    private void logHeader() {
        sampler.sample();
        logger.logHeader(new DataPoint(sampler.getProvidedFeatures()));
    }

    private void sampleAndLog() {
        sampler.sample();
        synchronized (logger) {
            logger.log(new DataPoint(sampler.getProvidedFeatures()));
        }
    }

    public void main(ExecutionManager manager) {
        prepareLogger();
        manager.setManagedClass(this);
        manager.startAsyncThread();
        if ( manager.hasEnded() ) return;

        prepareSampler();
        logHeader();
        while ( !manager.hasEnded() ) {
            sampleAndLog();
            Utils.sleepNoInterrupt(samplingPeriod);
            if ( shouldStop.get() )
                break;
        }
    }

    @Override
    public void end() {
        shouldStop.set(true);
        synchronized (logger) {
            logger.cleanup();
        }
    }
}
