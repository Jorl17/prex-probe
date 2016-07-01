import BuildingBlocks.DataPoint;
import DataLoggers.CsvLogger;
import DataLoggers.DataLogger;
import DataLoggers.PreXProbeLogger;
import DataLoggers.StdoutAndCsvLogger;
import DataSamplers.*;
import ExecutionManagers.ExecutionManager;
import ExecutionManagers.ManagedClass;
import Util.Utils;
import org.hyperic.sigar.Sigar;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jorl17 on 02/08/15.
 */
public class PrexProbe implements ManagedClass {
    private final String outCsv;
    private final boolean logToStdout;
    private final int samplingPeriod;
    private final boolean prex;
    private final String prexSrc;
    private final String prexHost;
    private final int prexPort;

    private DataLogger logger;
    private MultiFeatureSampler sampler;

    private static final String SEPARATOR = ", ";
    private static final int DEFAULT_SAMPLING_PERIOD = 100;

    private AtomicBoolean shouldStop;

    public PrexProbe(String outCsv, boolean logToStdout, boolean prex, String prexSrc, String prexHost, int prexPort, int samplingPeriod) {
        this.outCsv = outCsv;
        this.logToStdout = logToStdout;
        this.samplingPeriod = samplingPeriod;
        this.shouldStop = new AtomicBoolean(false);
        this.prex = prex;
        this.prexSrc = prexSrc;
        this.prexHost = prexHost;
        this.prexPort = prexPort;
    }

    public PrexProbe(String outCsv, boolean logToStdout, boolean prex, String prexSrc, String prexHost, int prexPort) {
        this(outCsv,logToStdout, prex, prexSrc, prexHost, prexPort, DEFAULT_SAMPLING_PERIOD);
    }

    private void prepareLogger() {
        if ( !this.prex )
            this.logger = logToStdout ? new StdoutAndCsvLogger(outCsv, SEPARATOR) : new CsvLogger(outCsv, SEPARATOR);
        else {
            try {
                this.logger = new PreXProbeLogger(prexSrc, prexHost, prexPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        // FileSystemSampler is disabled because it did not offer any new data and sometimes catches filesystems
        // twice (they *are* mounted twice)
        sampler = new MultiFeatureSampler(sigar, new CPUSampler(sigar), new MemorySampler(sigar)/*, new FileSystemSampler(sigar)*/, new TCPSampler(sigar), new NetIfaceSampler(sigar));
    }

    private void tryUnpackLibraries() {
        //FIXME: Unpack Sigar libraries here

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
