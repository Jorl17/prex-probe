package ExecutionManagers;

import Util.Utils;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * Created by jorl17 on 03/08/15.
 */
public class ForeverExecutionManager extends ExecutionManager {

    public ForeverExecutionManager() {
    }


    @Override
    public void run() {
        for(;;) {

            Utils.sleepNoInterrupt(60000);
        }
    }
}
