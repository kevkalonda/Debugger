package dbg.outils;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

public class CommandMethod implements Command{


    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        StackFrame stackFrame = ((LocatableEvent) event).thread().frame(0);
        ObjectReference objectReference = stackFrame.thisObject();

        System.out.println("La methode  en cours d execution :");
        System.out.println("\t "+stackFrame.location().method().returnType()+" " +stackFrame.location().method().name()+" ("+stackFrame.location().method().argumentTypeNames().toString()+")\n");
        System.out.println("le nom de la methode courante : " +stackFrame.location().method().name());
        System.out.println("Les parametres sont  : " +stackFrame.location().method().argumentTypeNames());
        System.out.println("Le type de retour est : " +stackFrame.location().method().returnType()+"\n");
        scriptableDebugger.choseMethode(event);

    }
}
