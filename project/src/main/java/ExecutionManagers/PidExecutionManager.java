package ExecutionManagers;

import Util.Utils;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 03/08/15.
 */
public class PidExecutionManager extends ExecutionManager {
    private long pid;
    private int monitorInterval;
    private final static int DEFAULT_MONITOR_INTERVAL = 1000;

    public PidExecutionManager(Sigar sigar, ManagedClass managedClass, long pid, int monitorInterval) {
        super(managedClass);
        this.pid = pid;
        this.monitorInterval = monitorInterval;
    }
    public PidExecutionManager(Sigar sigar, ManagedClass managedClass, long pid) {
        this(sigar, managedClass, pid, DEFAULT_MONITOR_INTERVAL);
    }

    public PidExecutionManager(ManagedClass managedClass, long pid) {
        this(null, managedClass, pid, DEFAULT_MONITOR_INTERVAL);
    }

    public PidExecutionManager(long pid, int monitorInterval) {
        this(null, null, pid, monitorInterval);
    }

    public PidExecutionManager(long pid) {
        this(null, null, pid, DEFAULT_MONITOR_INTERVAL);
    }

    @Override
    public void run() {
        for(;;) {

            if ( processEnded() ) break;

            Utils.sleepNoInterrupt(monitorInterval);
        }

        notifyEnd();
    }

    private boolean processEnded() {
        try {
            renewSigar();
            ProcState procState = getSigar().getProcState(pid);
            switch (procState.getState()) {
                case ProcState.SLEEP:
                case ProcState.RUN:
                case ProcState.STOP:
                case ProcState.ZOMBIE:
                case ProcState.IDLE:
                    return false;
            }
            return procState.getName() == null || procState.getName().isEmpty() || procState.getPpid() < 0;
        } catch (SigarException e) {
            e.printStackTrace();
            return true;
        }
    }
}
