package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueBreakCommand;

/**
 * CommandeBreak.class
 */
public class CommandeBreak implements Command {
    /**
     * Installe un point d’arrêt à la ligne
     * lineNumber du fichier fileName
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException       exception
     * @throws IncompatibleThreadStateException exception
     * @throws InterruptedException             exception
     * @throws ClassNotLoadedException          exception
     */
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws AbsentInformationException, IncompatibleThreadStateException, InterruptedException, ClassNotLoadedException {

        FabriqueBreakCommand.enableSetBreakPoint(event, scriptableDebugger);
    }
}
