package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

public class CommandArguments implements Command {
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        StackFrame stackFrame = ((LocatableEvent) event).thread().frame(0);
        ObjectReference objectReference = stackFrame.thisObject();


        if (stackFrame.location().method().arguments().size() == 0) {
            System.out.println("Aucun parametre pour la methode " + stackFrame.location().method());
        } else {
            System.out.println("Arguments :");
            for (LocalVariable arg : stackFrame.location().method().arguments()) {
                System.out.println(arg.name() + " = " + stackFrame.getValue(arg).toString() + " ; ");
            }
        }
        scriptableDebugger.choseMethode(event);
    }
}
