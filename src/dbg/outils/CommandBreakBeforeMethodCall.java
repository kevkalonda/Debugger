package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;

/**
 * La commande break-before-method-call(String methodName)
 */
public class CommandBreakBeforeMethodCall implements Command {

    /**
     * configure l’exécution
     * pour s’arrêter au tout début de l’exécution de la méthode methodName.
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
            String nameMethod = scriptableDebugger.getCmd().split(" ")[1];
            scriptableDebugger.enableBreakBeforeMethodCall(nameMethod);
        } else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 1 argument");
            System.err.println(("\t la nameMethod"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }
}
