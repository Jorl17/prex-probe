package ExecutionManagers;

import org.hyperic.sigar.Sigar;

/**
 * Created by jorl17 on 03/08/15.
 */
public abstract class ExecutionManager implements Runnable {
    private final ManagedClass managedClass;
    private Sigar sigar;

    public ExecutionManager(ManagedClass managedClass, Sigar sigar) {
        this.managedClass = managedClass;
        this.sigar = sigar != null ? sigar : new Sigar();
    }

    public ExecutionManager(ManagedClass managedClass) {
        this(managedClass, new Sigar());
    }

    protected void notifyEnd() {
        assert(managedClass != null);
        managedClass.end();
    }

    protected Sigar getSigar() {
        return sigar;
    }

    protected  void renewSigar() {
        sigar = new Sigar();
    }
}
