package dbg.outils;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.event.Event;
import dbg.ScriptableDebugger;

public class CommandStack implements Command{
    @Override
    public void execute(Event event, ScriptableDebugger scriptableDebugger) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {
        StackTraceElement[] stackframe =  Thread.currentThread().getStackTrace();
        for (StackTraceElement sf:stackframe
        ) {
            System.out.println(" Stack || "+sf);
        }
        scriptableDebugger.choseMethode(event);
    }
}
