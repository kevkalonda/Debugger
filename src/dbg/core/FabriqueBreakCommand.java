package dbg.core;

import com.sun.jdi.*;
import com.sun.jdi.event.Event;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.StepRequest;
import dbg.ScriptableDebugger;

/**
 * FabriqueBreakCommand.class
 */
public final class FabriqueBreakCommand {
    /**
     * Désactive tous les steps
     *
     * @param vm {@link VirtualMachine}
     */
    public static void disactiveAllStep(VirtualMachine vm) {
        for (StepRequest stepR : vm.eventRequestManager().stepRequests()) {
            stepR.disable();
        }
    }

    /**
     * Permet d'ajouter installe un point d’arrêt à la ligne
     * lineNumber du fichier className.
     *
     * @param className          {@link String}
     * @param lineNumber         {@link Integer}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException exception
     */
    public static void setBreakPoint(String className, int lineNumber, ScriptableDebugger scriptableDebugger) throws AbsentInformationException {
        disactiveAllStep(scriptableDebugger.getVm());
        className = "dbg." + className;
        for (ReferenceType targetClass : scriptableDebugger.getVm().allClasses()) {
            if (targetClass.name().equals(className)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest breakpointRequest = scriptableDebugger.getVm().eventRequestManager().createBreakpointRequest(location);
                breakpointRequest.enable();
            }
        }
    }

    /**
     * Installe un point d’arrêt à
     * la ligne lineNumber du fichier className. Ce point d’arrêt se désinstalle après
     * avoir été atteint.
     *
     * @param className          {@link String}
     * @param lineNumber         {@link Integer}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException exception
     */
    public static void setBreakPointOnce(String className, int lineNumber, ScriptableDebugger scriptableDebugger) throws AbsentInformationException {
        disactiveAllStep(scriptableDebugger.getVm());
        className = "dbg." + className;
        for (ReferenceType targetClass : scriptableDebugger.getVm().allClasses()) {
            if (targetClass.name().equals(className)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest breakpointRequest = scriptableDebugger.getVm().eventRequestManager().createBreakpointRequest(location);
                breakpointRequest.enable();
                scriptableDebugger.getBreakpointRequestList().add(breakpointRequest);
            }
        }
    }

    /**
     * Installe un point d’arrêt à la ligne lineNumber du fichier fileName. Ce point d’arrêt ne
     * s’active qu’après avoir été atteint un certain nombre de fois count
     *
     * @param className  {@link String}
     * @param lineNumber {@link Integer}
     * @param count      {@link Integer}
     * @throws AbsentInformationException exception
     */
    public static void setBreakOnCount(String className, int lineNumber, int count, ScriptableDebugger scriptableDebugger) throws AbsentInformationException {
        disactiveAllStep(scriptableDebugger.getVm());
        className = "dbg." + className;
        for (ReferenceType targetClass : scriptableDebugger.getVm().allClasses()) {
            if (targetClass.name().equals(className)) {

                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest breakpointRequest = scriptableDebugger.getVm().eventRequestManager().createBreakpointRequest(location);
                breakpointRequest.disable();
                scriptableDebugger.getBreakpointRequestCount().setCount(count);
                scriptableDebugger.getBreakpointRequestCount().setClassName(className);
                scriptableDebugger.getBreakpointRequestCount().setLineNumber(lineNumber);
                scriptableDebugger.getBreakpointRequestCount().setBreakpointRequest(breakpointRequest);
            }

        }
    }

    /**
     * Vérifie si le nombre d'arguments passé en paramètre correspond pour exécuter la commande.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException       exception
     * @throws ClassNotLoadedException          exception
     * @throws IncompatibleThreadStateException exception
     * @throws InterruptedException             exception
     */
    public static void enableBreakOnCount(Event event, ScriptableDebugger scriptableDebugger) throws AbsentInformationException, ClassNotLoadedException, IncompatibleThreadStateException, InterruptedException {
        if (scriptableDebugger.getCmd().split(" ").length == 4) {
            String className = scriptableDebugger.getCmd().split(" ")[1];
            String numberLine = scriptableDebugger.getCmd().split(" ")[2];
            int count = Integer.parseInt(scriptableDebugger.getCmd().split(" ")[3]);
            int lineNumber = Integer.parseInt(numberLine);
            setBreakOnCount(className, lineNumber, count, scriptableDebugger);
        } else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 3 arguments");
            System.err.println(("\t la className , lineNumber et count"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }

    /**
     * Vérifie si le nombre d'arguments passé en paramètre correspond pour exécuter la commande.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException       exception
     * @throws ClassNotLoadedException          exception
     * @throws IncompatibleThreadStateException exception
     * @throws InterruptedException             exception
     */
    public static void enableSetBreakPoint(Event event, ScriptableDebugger scriptableDebugger) throws ClassNotLoadedException, IncompatibleThreadStateException, AbsentInformationException, InterruptedException {
        if (scriptableDebugger.getCmd().split(" ").length == 3) {
            String className = scriptableDebugger.getCmd().split(" ")[1];
            String numberLine = scriptableDebugger.getCmd().split(" ")[2];
            int lineNumber = Integer.parseInt(numberLine);
            setBreakPoint(className, lineNumber, scriptableDebugger);
        } else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 2 arguments");
            System.err.println(("\t la className et lineNumber"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }

    /**
     * Vérifie si le nombre d'arguments passé en paramètre correspond pour exécuter la commande.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException       exception
     * @throws ClassNotLoadedException          exception
     * @throws IncompatibleThreadStateException exception
     * @throws InterruptedException             exception
     */
    public static void enableSetBreakPointOnce(Event event, ScriptableDebugger scriptableDebugger) throws AbsentInformationException, ClassNotLoadedException, IncompatibleThreadStateException, InterruptedException {
        if (scriptableDebugger.getCmd().split(" ").length == 3) {
            String className = scriptableDebugger.getCmd().split(" ")[1];
            String numberLine = scriptableDebugger.getCmd().split(" ")[2];
            int lineNumber = Integer.parseInt(numberLine);
            setBreakPointOnce(className, lineNumber, scriptableDebugger);
        } else {
            System.err.println("La commande '" + scriptableDebugger.getCmd().split(" ")[0] + "' doit avoir 2 arguments");
            System.err.println(("\t la className et lineNumber"));
            Thread.sleep(200);
        }
        scriptableDebugger.choseMethode(event);
    }

    /**
     * Vérifie si le nombre d'arguments passé en paramètre correspond pour exécuter la commande.
     *
     * @param event              {@link Event}
     * @param scriptableDebugger {@link ScriptableDebugger}
     * @throws AbsentInformationException       exception
     * @throws ClassNotLoadedException          exception
     * @throws IncompatibleThreadStateException exception
     * @throws InterruptedException             exception
     */
    public static void enableBreakPoints(Event event, ScriptableDebugger scriptableDebugger) throws AbsentInformationException, ClassNotLoadedException, IncompatibleThreadStateException, InterruptedException {
        System.out.println("Liste des points d arrets actifs et leurs location  :");
        for (BreakpointRequest breakpointRequest : scriptableDebugger.getVm().eventRequestManager().breakpointRequests()) {
            if (breakpointRequest.isEnabled()) {
                System.out.println("\t Class: " + breakpointRequest.location().sourceName() + " ligne: " + breakpointRequest.location().lineNumber());
            }
        }
        scriptableDebugger.choseMethode(event);
    }

}
