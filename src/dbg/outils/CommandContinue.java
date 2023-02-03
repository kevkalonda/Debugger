package dbg.outils;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueCommand;

/**
 * CommandContinue.class
 */
public class CommandContinue implements Command {
    /**
     * Continue l’exécution jusqu’au prochain point d’arrêt. La granularité est l’instruction step.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     */
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) {
        FabriqueCommand.enableStepContinueRequest((LocatableEvent) event, scriptableDebugger);
    }
}
