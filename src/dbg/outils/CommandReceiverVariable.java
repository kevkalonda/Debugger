package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

import java.util.Map;

/**
 * CommandReceiverVariable.class
 */
public class CommandReceiverVariable implements Command {
    /**
     * Renvoie et imprime la liste des variables d’instance du
     * receveur courant, sous la forme d’un couple nom → valeur.
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
        StackFrame stackFrame = ((LocatableEvent) event).thread().frame(0);
        ObjectReference objectReference = stackFrame.thisObject();

        if (objectReference == null) {
            System.out.println("No Receiver Variables: " + stackFrame.location().method());
        } else {
            Map<Field, Value> fields = objectReference.getValues(objectReference.referenceType().allFields());
            for (Map.Entry<Field, Value> field : fields.entrySet()) {
                System.out.println(field.getKey().name() + " = " + field.getValue());
            }
        }
        scriptableDebugger.choseMethode(event);
    }
}
