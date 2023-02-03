package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

/**
 * CommandPrintVar.class
 */
public class CommandPrintVar implements Command {
    /**
     * Imprime la valeur de la variable passée en
     * paramètre
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws IncompatibleThreadStateException exception
     * @throws AbsentInformationException       exception
     * @throws InterruptedException             exception
     * @throws ClassNotLoadedException          exception
     */
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
        } else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 1 argument");
            System.err.println(("\t varName"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }
}
