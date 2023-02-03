package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;

/**
 * CommandStack.class
 */
public class CommandStack implements Command {
    /**
     * Renvoie la pile d’appel de méthodes qui a amené l’exécution au point courant.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws IncompatibleThreadStateException exception
     * @throws AbsentInformationException       exception
     * @throws InterruptedException             exception
     * @throws ClassNotLoadedException          exception
     */
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        StackTraceElement[] stackframe = Thread.currentThread().getStackTrace();
        for (StackTraceElement sf : stackframe
        ) {
            System.out.println(" Stack || " + sf);
        }
        scriptableDebugger.choseMethode(event);
    }
}
