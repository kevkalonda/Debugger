package dbg.outils;

import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueCommand;

public class CommandStep implements Command{

    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) {
        FabriqueCommand.enableStepRequest((LocatableEvent) event,scriptableDebugger);
    }
}
