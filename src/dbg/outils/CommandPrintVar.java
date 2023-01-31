package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;


public class CommandPrintVar implements Command {
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        if (scriptableDebugger.getCmd().split(" ").length == 2) {
            StackFrame stackFrame = ((LocatableEvent) event).thread().frame(0);
            String varName = scriptableDebugger.getCmd().split(" ")[1];
            LocalVariable localVariable = stackFrame.visibleVariableByName(varName);
            if (localVariable != null) {
                Value value = stackFrame.getValue(localVariable);
                // System.out.printf("Variable passé en params : %s",value);
                System.out.println("Variable " + varName + " = " + value + " ;");
            } else {
                System.out.println("La variable n'existe pas");
            }
        }else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 1 argument");
            System.err.println(("\t varName"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }
}
