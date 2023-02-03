package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

/**
 * Commande Arguments.
 */
public class CommandArguments implements Command {
    /**
     * Renvoie et
     * imprime la liste des arguments de la méthode en cours d’exécution, sous la forme d’un couple nom -> valeur.
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
        // Vérifie si la methode contient des arguments.
        if (stackFrame.location().method().arguments().size() == 0) {
            System.out.println("Aucun parametre pour la methode " + stackFrame.location().method());
        } else {
            System.out.println("Arguments :");
            for (LocalVariable arg : stackFrame.location().method().arguments()) {
                System.out.println(arg.name() + " = " + stackFrame.getValue(arg).toString() + " ; ");
            }
        }
        // Permet de redonner la main à l'exécutable.
        scriptableDebugger.choseMethode(event);
    }
}
