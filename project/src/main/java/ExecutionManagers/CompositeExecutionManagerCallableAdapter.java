package ExecutionManagers;

import java.util.concurrent.Callable;

/**
 * Created by jorl17 on 03/08/15.
 */
public class CompositeExecutionManagerCallableAdapter implements Callable<Boolean> {
    private ExecutionManager manager;

    public CompositeExecutionManagerCallableAdapter(ExecutionManager manager) {
        this.manager = manager;
    }

    @Override
    public Boolean call() throws Exception {
        manager.run();
        return true;
    }
}
