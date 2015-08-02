import BuildingBlocks.DataPoint;
import DataLoggers.CsvLogger;
import DataLoggers.DataLogger;
import DataLoggers.StdoutAndCsvLogger;
import DataLoggers.StdoutLogger;
import DataSamplers.*;
import Features.Feature;
import Util.Utils;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;

public class Main {

    private static class Parameters {
        @Parameter(names = {"-c", "--csv"}, description = "Output CSV file")
        private String csv = "test.csv";

        @Parameter(names = {"-s", "--stdout"}, description = "Also log to stdout")
        private boolean logStdout = false;

        @Parameter(names = {"-p", "--period"}, description = "Sampling period")
        private int samplingPeriod = 1000;

        @Parameter(names = {"-h", "--help"}, help = true)
        private boolean help;

    }

    public static void main(String[] args) throws SigarException {
        Parameters parameters = new Parameters();
        JCommander jcmd = new JCommander(parameters, args);
        if (parameters.help) {
            jcmd.usage();
            return;
        }

        MiniPrexMonitor miniPrexMonitor = new MiniPrexMonitor(parameters.csv, parameters.logStdout, parameters.samplingPeriod);
        miniPrexMonitor.main();
    }
}
