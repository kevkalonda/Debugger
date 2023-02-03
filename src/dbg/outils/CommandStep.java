package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueCommand;

public class CommandStep implements Command {
    /**
     * Execute la prochaine instruction. S’il s’agit d’un appel de méthode,
     * l’exécution entre dans cette dernière
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws IncompatibleThreadStateException exception
     * @throws AbsentInformationException       exception
     * @throws InterruptedException             exception
     * @throws ClassNotLoadedException          exception
     */
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) {
        FabriqueCommand.enableStepRequest((LocatableEvent) event, scriptableDebugger);
    }
}
