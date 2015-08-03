package ExecutionManagers;

import org.hyperic.sigar.Sigar;

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

        // Set a dummy listener for all of them
        for (ExecutionManager m : managers)
            m.setManagedClass(() -> {});
    }

    @Override
    public void run() {
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
            assert(m.hasEnded());

        executorService.shutdown();
        notifyEnd();
    }
}
