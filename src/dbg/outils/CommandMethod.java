package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

/**
 * CommandMethod.class
 */
public class CommandMethod implements Command {

    /**
     * Renvoie et imprime la méthode en cours d’exécution.
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

        System.out.println("La methode  en cours d execution :");
        System.out.println("\t " + stackFrame.location().method().returnType() + " " + stackFrame.location().method().name() + " (" + stackFrame.location().method().argumentTypeNames().toString() + ")\n");
        System.out.println("le nom de la methode courante : " + stackFrame.location().method().name());
        System.out.println("Les parametres sont  : " + stackFrame.location().method().argumentTypeNames());
        System.out.println("Le type de retour est : " + stackFrame.location().method().returnType() + "\n");
        // Redonne la main à l'exécutable
        scriptableDebugger.choseMethode(event);

    }
}
