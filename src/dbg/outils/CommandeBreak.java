package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;
import dbg.core.FabriqueBreakCommand;

public class CommandeBreak implements Command {
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws AbsentInformationException, IncompatibleThreadStateException, InterruptedException, ClassNotLoadedException {
        /*String className = scriptableDebugger.getCmd().split(" ")[1];
        String numberLine = scriptableDebugger.getCmd().split(" ")[2];
        int lineNumber =  Integer.parseInt(numberLine);
        //scriptableDebugger.enableBreakRequest((LocatableEvent) event);
        scriptableDebugger.enableSetBreakPoint(className, lineNumber);
        scriptableDebugger.choseMethode(event);*/
        FabriqueBreakCommand.enableSetBreakPoint(event, scriptableDebugger);
    }
}
