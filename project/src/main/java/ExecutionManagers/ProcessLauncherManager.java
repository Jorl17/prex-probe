package ExecutionManagers;

import java.io.IOException;

/**
 * Created by jorl17 on 03/08/15.
 */
public class ProcessLauncherManager extends ExecutionManager {
    private final String command;
    public ProcessLauncherManager(ManagedClass managedClass, String command) {
        super(managedClass);
        this.command = command;
    }

    @Override
    public void run() {
        Process p = runCommand();
        if ( p == null )
            return;

        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        notifyEnd();
    }

    private Process runCommand() {
        Runtime rt = Runtime.getRuntime();
        try {
            return rt.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to run command: " + command + "!");
            return null;
        }
    }
}
