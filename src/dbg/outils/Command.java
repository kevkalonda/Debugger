package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;

public interface Command {

    void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException;
}
