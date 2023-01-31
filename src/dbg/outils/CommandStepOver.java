package dbg.outils;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueCommand;

public class CommandStepOver implements Command {
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) {
        FabriqueCommand.enableStepOverRequest((LocatableEvent) event, scriptableDebugger);
    }
}
