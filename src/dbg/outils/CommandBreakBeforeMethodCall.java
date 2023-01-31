package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;

public class CommandBreakBeforeMethodCall implements Command{
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        if (scriptableDebugger.getCmd().split(" ").length == 2) {
            String nameMethod = scriptableDebugger.getCmd().split(" ")[1];
            scriptableDebugger.enableBreakBeforeMethodCall(nameMethod);
        }else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 1 argument");
            System.err.println(("\t la nameMethod"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }
}
