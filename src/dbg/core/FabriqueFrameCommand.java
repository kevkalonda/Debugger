package dbg.core;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.LocatableEvent;
import dbg.ScriptableDebugger;

public final class FabriqueFrameCommand {
    public static void enableCommandFrame(Event event, ScriptableDebugger scriptableDebugger) throws ClassNotLoadedException, IncompatibleThreadStateException, AbsentInformationException, InterruptedException {
        StackTraceElement[] stackframe =  Thread.currentThread().getStackTrace();
        System.out.println(" frame || "+stackframe[0]);
        scriptableDebugger.choseMethode(event);
    }

    public static void enableCommandReceiver(Event event, ScriptableDebugger scriptableDebugger) throws ClassNotLoadedException, IncompatibleThreadStateException, AbsentInformationException, InterruptedException {
        StackFrame stackFrame = ((LocatableEvent) event).thread().frame(0);
        ObjectReference ob = stackFrame.thisObject();
        if (ob == null) {
            System.out.println("Receiver Class: " + stackFrame.location().method());
        } else {
            System.out.println("Receiver : " + ob);
        }
        scriptableDebugger.choseMethode(event);
    }

    public static void enableSender(Event event, ScriptableDebugger scriptableDebugger) throws ClassNotLoadedException, IncompatibleThreadStateException, AbsentInformationException, InterruptedException {
        if (((LocatableEvent)event).thread().frames().size()==1) {
            System.out.println("frame(1)/ Sender n'existe pas! \nLa methode courante est : "+ ((LocatableEvent)event).thread().frame(0).location().method().name()+" ; ");
        }else {
            StackFrame stackFrame = ( (LocatableEvent)event).thread().frame(1);
            ObjectReference  objectReference = stackFrame.thisObject();
            if(objectReference == null){
                System.out.println("Sender does not existe because of static class : "+stackFrame.location().method().signature()+" :" +stackFrame.location().method().name()+" ; ");
            }else{
                System.out.println("Sender  : "+objectReference+" ; ");
            }
        }
        scriptableDebugger.choseMethode(event);
    }
}
