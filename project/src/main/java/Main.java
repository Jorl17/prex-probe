import ExecutionManagers.CompositeExecutionManager;
import ExecutionManagers.ExecutionManager;
import ExecutionManagers.PidExecutionManager;
import ExecutionManagers.ProcessLauncherManager;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static class Parameters {
        @Parameter(names = {"-c", "--csv"}, description = "Output CSV file")
        private String csv = "test.csv";

        @Parameter(names = {"-s", "--stdout"}, description = "Also log to stdout")
        private boolean logStdout = false;

        @Parameter(names = {"-p", "--period"}, description = "Sampling period")
        private int samplingPeriod = 1000;

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

        CompositeExecutionManager manager = new CompositeExecutionManager(executionManagers);
        MiniPrexMonitor miniPrexMonitor = new MiniPrexMonitor(parameters.csv, parameters.logStdout, parameters.samplingPeriod);

        miniPrexMonitor.main(manager);
    }
}
