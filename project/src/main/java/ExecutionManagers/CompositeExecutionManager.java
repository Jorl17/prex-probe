package ExecutionManagers;

import org.hyperic.sigar.Sigar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jorl17 on 03/08/15.
 */
public class CompositeExecutionManager extends ExecutionManager {
    private ExecutionManager[] managers;
    public CompositeExecutionManager(ManagedClass managedClass, ExecutionManager... managers) {
        super(managedClass);
        this.managers = managers;

        // Set a dummy listener for all of the other managers
        for (ExecutionManager m : managers)
            m.setManagedClass(() -> {});
    }
    public CompositeExecutionManager(ExecutionManager... managers) {
        this(null, managers);
    }

    public CompositeExecutionManager(ManagedClass managedClass, ArrayList<ExecutionManager> managers) {
        super(managedClass);
        this.managers = new ExecutionManager[managers.size()];
        for (int i = 0; i < this.managers.length; i++)
            this.managers[i] = managers.get(i);

        // Set a dummy listener for all of the other managers
        for (ExecutionManager m : managers)
            m.setManagedClass(() -> {});
    }

    public CompositeExecutionManager(ArrayList<ExecutionManager> managers) {
        this(null, managers);
    }

    @Override
    public void run() {
        if ( managers.length > 0 ) {
            Set<CompositeExecutionManagerCallableAdapter> callables = new HashSet<>();
            for (ExecutionManager m : managers)
                callables.add(new CompositeExecutionManagerCallableAdapter(m));

            ExecutorService executorService = Executors.newFixedThreadPool(callables.size());
            try {
                executorService.invokeAll(callables);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (ExecutionManager m : managers)
                assert (m.hasEnded());
            executorService.shutdown();
        }

        notifyEnd();
    }

    @Override
    public boolean hasEnded() {
        return super.hasEnded() || this.managers.length == 0;
    }
}
