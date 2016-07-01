import ExecutionManagers.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static class Parameters {
        @Parameter(names = {"-c", "--csv"}, description = "Output CSV file (not compatible with PreX)")
        private String csv = "test.csv";

        @Parameter(names = {"-s", "--stdout"}, description = "Also log to stdout when outputting to CSV")
        private boolean logStdout = false;

        @Parameter(names = {"-f", "--forever"}, description = "Monitor forever")
        private boolean forever = false;

        @Parameter(names = {"-p", "--period"}, description = "Sampling period")
        private int samplingPeriod = 1000;

        @Parameter(names = {"-prex", "--prex-probe"}, description = "Act as PreX probe with the given src name")
        private String prexSrc = null;

        @Parameter(names = {"-ph", "--prex-host"}, description = "PreX Host")
        private String prexHost = "localhost";

        @Parameter(names = {"-pp", "--prex-port"}, description = "PreX Port")
        private int prexPort = 1610;

        @Parameter(names = {"-l", "--launch"}, description = "Launch application/command and wait until it ends")
        private List<String> launchers = new ArrayList<>();

        @Parameter(names = {"-pid", "--pid"}, description = "Wait until process with given PID has ended")
        private List<Long> pids = new ArrayList<>();

        @Parameter(names = {"-h", "--help"}, help = true)
        private boolean help;

    }

    private static ArrayList<ExecutionManager> launchersToProcessLauncherArrayList(List<String> launchers) {
        ArrayList<ExecutionManager> ret = new ArrayList<>();
        for ( String l : launchers)
            ret.add(new ProcessLauncherManager(l));
        return ret;
    }

    private static ArrayList<ExecutionManager> pidToPidExecutionManagerArrayList(List<Long> pids) {
        ArrayList<ExecutionManager> ret = new ArrayList<>();
        for ( long p : pids)
            ret.add(new PidExecutionManager(p));
        return ret;
    }

    public static void main(String[] args) throws SigarException {
        Parameters parameters = new Parameters();
        JCommander jcmd = new JCommander(parameters, args);
        if (parameters.help) {
            jcmd.usage();
            return;
        }
        ArrayList<ExecutionManager> executionManagers = new ArrayList<>();
        executionManagers.addAll(launchersToProcessLauncherArrayList(parameters.launchers));
        executionManagers.addAll(pidToPidExecutionManagerArrayList(parameters.pids));

        if ( parameters.forever )
            executionManagers.add(new ForeverExecutionManager());

        CompositeExecutionManager manager = new CompositeExecutionManager(executionManagers);
        PrexProbe prexProbe = new PrexProbe(parameters.csv, parameters.logStdout, parameters.prexSrc != null, parameters.prexSrc, parameters.prexHost, parameters.prexPort, parameters.samplingPeriod);

        prexProbe.main(manager);
    }
}
