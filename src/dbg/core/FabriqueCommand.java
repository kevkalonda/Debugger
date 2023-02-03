package dbg.core;

import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.request.StepRequest;
import dbg.ScriptableDebugger;

public final class FabriqueCommand {

    /**
     * Dans cette méthode, nous configurons et activons une requête de stepping
     *
     * @param event {@link LocatableEvent}
     */
    public static void enableStepRequest(LocatableEvent event, ScriptableDebugger scriptableDebugger) {
        if (scriptableDebugger.getStep() == null) {
            StepRequest step = scriptableDebugger.getVm().eventRequestManager().createStepRequest(
                    event.thread(),
                    StepRequest.STEP_LINE,
                    StepRequest.STEP_INTO
            );
            scriptableDebugger.setStep(step);
        }
        scriptableDebugger.getStep().enable();
    }

    /**
     * Dans cette méthode, nous configurons et activons une requête de step-over.
     *
     * @param event              {@link LocatableEvent}
     * @param scriptableDebugger {@link ScriptableDebugger}
     */
    public static void enableStepOverRequest(LocatableEvent event, ScriptableDebugger scriptableDebugger) {
        if (scriptableDebugger.getStepOver() == null) {
            StepRequest stepOver = scriptableDebugger.getVm().eventRequestManager().createStepRequest(
                    event.thread(),
                    StepRequest.STEP_LINE,
                    StepRequest.STEP_OVER
            );
            scriptableDebugger.setStepOver(stepOver);
        }
        scriptableDebugger.getStepOver().enable();
    }

    /**
     * Dans cette méthode, nous configurons et activons une requête continue.
     * @param event {@link LocatableEvent}
     * @param scriptableDebugger {@link ScriptableDebugger}
     */
    public static void enableStepContinueRequest(LocatableEvent event, ScriptableDebugger scriptableDebugger) {
        event.request().disable();
    }

}