package dbg.outils;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueCommand;

public class CommandContinue implements Command{
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) {
        FabriqueCommand.enableStepContinueRequest((LocatableEvent) event, scriptableDebugger);
    }
}
