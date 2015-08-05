package ExecutionManagers;

import org.hyperic.sigar.Sigar;

/**
 * Created by jorl17 on 03/08/15.
 */
public abstract class ExecutionManager implements Runnable {
    private ManagedClass managedClass;
    private boolean ended;
    private Sigar sigar;

    public ExecutionManager(ManagedClass managedClass, Sigar sigar) {
        this.managedClass = managedClass;
        this.sigar = sigar != null ? sigar : new Sigar();
        this.ended = false;
    }

    public ExecutionManager(ManagedClass managedClass) {
        this(managedClass, new Sigar());
    }

    public ExecutionManager() {
        this(null, null);
    }

    protected void notifyEnd() {
        assert(managedClass != null);
        assert(!this.ended);
        this.ended = true;
        managedClass.end();
    }

    public void startAsyncThread() {
        new Thread(this).start();
    }

    protected Sigar getSigar() {
        return sigar;
    }

    protected  void renewSigar() {
        sigar = new Sigar();
    }

    public boolean hasEnded() {
        return this.ended;
    }

    // The only reason why we don't make the managedclass a final field is so that we can change it in the
    // CompositeExecutionManager
    public void setManagedClass(ManagedClass managedClass) {
        this.managedClass = managedClass;
    }
}
