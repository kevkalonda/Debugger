package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueBreakCommand;

/**
 * CommandBreakOnCount.class
 */
public class CommandBreakOnCount implements Command {
    /**
     * Installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt ne
     * s’active qu’après avoir été atteint un certain nombre de fois count
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
        FabriqueBreakCommand.enableBreakOnCount(event, scriptableDebugger);
    }
}
