package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandTemporaries.Class
 */
public class CommandTemporaries implements Command {
    /**
     * Renvoie et imprime la liste des variables temporaires de la frame
     * courante, sous la forme de couples nom -> valeur.
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
        System.out.println("StackFrame : " + stackFrame);
        List<Value> res = new ArrayList<>();

        try {
            Map<String, Value> var = new HashMap<>();
            for (LocalVariable localVariable : stackFrame.visibleVariables()) {

                var.put(localVariable.name(), stackFrame.getValue(localVariable));
                res.add(var.get(localVariable.name()));
            }
            System.out.println("Name  ==> Value");
            for (int i = 0; i < var.size(); i++) {
                System.out.println(stackFrame.visibleVariables().get(i).name() + " ==> " + var.get(stackFrame.visibleVariables().get(i).name()) + " ;  ");
            }

        } catch (AbsentInformationException ex) {
            try {
                if (stackFrame.location().method().argumentTypes().size() == 0) {
                    System.out.println("Current Frame is empty");
                }
            } catch (ClassNotLoadedException ex2) {
                // ignore since the method is hit.
            }

            int argId = 0;
            try {
                List<Value> arguments = stackFrame.getArgumentValues();
                if (arguments == null) {
                    System.out.println("arguments == null");
                }
                for (Value argValue : res) {
                    System.out.println("Variable : " + argValue);
                }
            } catch (InternalException ex2) {
                if (ex2.errorCode() != 32) {
                    throw ex;
                }
            }
        }
        // Redonne la main à l'exécution
        scriptableDebugger.choseMethode(event);
    }
}
