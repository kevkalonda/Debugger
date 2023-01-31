package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueBreakCommand;

public class CommandBreakPoints implements Command{
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        FabriqueBreakCommand.enableBreakPoints(event, scriptableDebugger);
    }
}
