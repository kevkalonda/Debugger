package dbg;

import com.sun.jdi.*;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.StepRequest;
import dbg.core.BreakpointRequestCount;
import dbg.core.Error;
import dbg.outils.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class ScriptableDebugger {
    // Le main du programme à debugger
    private Class debugClass;
    // La machine virtuelle sur laquelle s’exécute la debugClass
    private VirtualMachine vm;
    /**
     * {@inheritDoc}
     */
    List<BreakpointRequest> breakpointRequestList;

    int count;
    Error error;
    /**
     * {@inheritDoc}
     */
    BreakpointRequestCount breakpointRequestCount;
    /**
     * {@inheritDoc}
     */
    List<String> nameMethods;

    String cmd;
    /**
     * {@inheritDoc}
     */
    Map<String, Command> commands = new HashMap<>();
    /**
     * {@inheritDoc}
     */
    StepRequest step = null;
    /**
     * {@inheritDoc}
     */
    StepRequest stepOver = null;

    public Error getError() {
        return error;
    }

    /**
     * Accesseur sur le {@link  BreakpointRequestCount}
     *
     * @return {@link  BreakpointRequestCount}
     */
    public BreakpointRequestCount getBreakpointRequestCount() {
        return breakpointRequestCount;
    }

    /**
     * Retourne la commande
     *
     * @return {@link String}
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * Accesseur sur le {@link  StepRequest}
     *
     * @return {@link  StepRequest}
     */
    public StepRequest getStep() {
        return step;
    }

    /**
     * Accesseur sur le {@link  StepRequest}
     *
     * @return {@link  StepRequest}
     */
    public StepRequest getStepOver() {
        return stepOver;
    }

    /**
     * Accesseur sur le {@link  StepRequest}
     *
     * @param s {@link  StepRequest}
     */
    public void setStep(StepRequest s) {
        step = s;
    }

    /**
     * Accesseur sur le {@link  StepRequest}
     *
     * @param s {@link  StepRequest}
     */
    public void setStepOver(StepRequest s) {
        stepOver = s;
    }

    /**
     * Accesseur sur le {@link  VirtualMachine}
     *
     * @return {@link  VirtualMachine}
     */
    public VirtualMachine getVm() {
        return vm;
    }

    /**
     * Accesseur sur la liste {@link  BreakpointRequest}
     *
     * @return une liste {@link  BreakpointRequest}
     */
    public List<BreakpointRequest> getBreakpointRequestList() {
        return breakpointRequestList;
    }

    /**
     * Initialisation des commandes
     */
    public void createCommands() {
        breakpointRequestCount = new BreakpointRequestCount();
        breakpointRequestList = new ArrayList<>();
        nameMethods = new ArrayList<>();
        error = new Error();
        count = 0;

        commands.put("step", new CommandStep());
        commands.put("step-over", new CommandStepOver());
        commands.put("continue", new CommandContinue());
        commands.put("receiver", new CommandReceiver());
        commands.put("frame", new CommandFrame());
        commands.put("temporaries", new CommandTemporaries());
        commands.put("stack", new CommandStack());
        commands.put("break", new CommandeBreak());
        commands.put("breakpoints", new CommandBreakPoints());
        commands.put("break-once", new CommandBreakOnce());
        commands.put("break-on-count", new CommandBreakOnCount());
        commands.put("break-before-method-call", new CommandBreakBeforeMethodCall());
        commands.put("print-var", new CommandPrintVar());
        commands.put("method", new CommandMethod());
        commands.put("arguments", new CommandArguments());
        commands.put("sender", new CommandSender());
        commands.put("receiver-variables", new CommandReceiverVariable());
    }

    /**
     * Connexion sur la vm
     *
     * @return {@link VirtualMachine}
     * @throws IOException                        exception
     * @throws IllegalConnectorArgumentsException exception
     * @throws VMStartException                   exception
     */
    public VirtualMachine connectAndLaunchVM() throws IOException, IllegalConnectorArgumentsException, VMStartException {
        LaunchingConnector launchingConnector = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> arguments = launchingConnector.defaultArguments();
        arguments.get("main").setValue(debugClass.getName());
        VirtualMachine vm = launchingConnector.launch(arguments);
        return vm;
    }

    /**
     * Cette méthode renseigne la
     * debugClass, instancie une machine virtuelle (connectAndLaunchVM()) puis
     * démarre la session de debugging (startDebugger()).
     *
     * @param debuggeeClass {@link Class}
     */
    public void attachTo(Class debuggeeClass) {

        this.debugClass = debuggeeClass;
        try {
            /**
             * connectAndLaunchVM() utilise l’API JDI pour instancier
             *  une interface vers une machine virtuelle pour le manipuler.
             */
            createCommands();
            vm = connectAndLaunchVM();
            // Interception de la preparation du debugClass.
            enableClassPrepareRequest(vm);
            startDebugger(); //demarage de la session.

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalConnectorArgumentsException e) {
            e.printStackTrace();
        } catch (VMStartException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (VMDisconnectedException e) {
            System.out.println("Virtual Machine is disconnected: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode configure une requête envoyée à la VM.
     * Pour chaque requête
     * active, la VM génère l’évènement correspondant lors de l’exécution du debuggee.
     *
     * @param vm {@link VirtualMachine}
     */
    public void enableClassPrepareRequest(VirtualMachine vm) {
        ClassPrepareRequest classPrepareRequest = vm.eventRequestManager().createClassPrepareRequest();
        classPrepareRequest.addClassFilter(debugClass.getName());
        classPrepareRequest.enable();
    }

    /**
     * configure une requête pour placer et activer un point d’arrêt dans la classe nommée
     * className à la ligne lineNumber.
     *
     * @param className  {@link String}
     * @param lineNumber {@link Integer}
     * @throws AbsentInformationException
     */
    public void setBreakPoint(String className, int lineNumber) throws AbsentInformationException {
        //System.out.println(className);
        for (ReferenceType targetClass : vm.allClasses()) {
            if (targetClass.name().equals(className)) {
                Location location = targetClass.locationsOfLine(lineNumber).get(0);
                BreakpointRequest breakpointRequest = vm.eventRequestManager().createBreakpointRequest(location);
                breakpointRequest.enable();
            }
        }
    }

    /**
     * Méthode qui active un breakpoints.
     *
     * @param event {@link StepEvent}
     */
    public void activeBreakCount(StepEvent event) {
        if (event.location().lineNumber() == breakpointRequestCount.getLineNumber()) {
            count++;
            if (breakpointRequestCount.getCount() == this.count) {
                System.out.println("breakPointCount activate");
                breakpointRequestCount.getBreakpointRequest().enable();
            }
        }
    }

    /**
     * Ajoute dans la liste de noms des méthodes pour la command BreakBeforeMethodCall
     *
     * @param methodName {@link String}
     */
    public void enableBreakBeforeMethodCall(String methodName) {
        this.nameMethods.add(methodName);
    }

    /**
     * Arrête l'exécution au tout début de l’exécution de la méthode methodName se trouvant dans la liste de 'nameMethods'
     *
     * @param event {@link  StepEvent}
     */
    public void exitProgMethod(StepEvent event) {
        for (String nameMethod : this.nameMethods) {
            if (event.location().method().name().equals(nameMethod)) {
                System.exit(0);
            }
        }
    }

    /**
     * Desinstalle un breakpoints après l'avoir depasser.
     *
     * @param event {@link  Event}
     * @throws AbsentInformationException exception
     */
    public void uninstallBreakAfterReach(Event event) throws AbsentInformationException {
        BreakpointRequest bp = null;
        for (BreakpointRequest breakpointRequest : breakpointRequestList) {
            System.out.println(breakpointRequest.toString());
            if (((StepEvent) event).location().lineNumber() == breakpointRequest.location().lineNumber() &&
                    ((StepEvent) event).location().sourceName().equals(breakpointRequest.location().sourceName())
            ) {
                System.out.println("remove breakpoints");
                breakpointRequest.disable();
                bp = breakpointRequest;
            }
        }
        if (bp != null) {
            breakpointRequestList.remove(bp);
        }
    }

    /**
     * @param event {@link  Event}
     * @throws IncompatibleThreadStateException exception
     * @throws AbsentInformationException       exception
     * @throws InterruptedException             exception
     * @throws ClassNotLoadedException          exception
     */
    public void choseMethode(Event event) throws IncompatibleThreadStateException, AbsentInformationException, InterruptedException, ClassNotLoadedException {

        System.out.print("Veuillez saisir une commande : ");
        Scanner scanner = new Scanner(System.in);
        cmd = scanner.nextLine();
        String executeCmd = cmd.split(" ")[0];
        if (commands.containsKey(executeCmd)) {
            commands.get(executeCmd).execute(event, this);
        } else {
            error.setMessage("Erreur Command \n" + "la commande '" + executeCmd + "' n'existe pas");
            System.err.println(error.getMessage());
            Thread.sleep(200);
            error.reset();
            choseMethode(event);
        }
    }

    /**
     * Launch le debbuger avec un breakpoints dans la class JDISimpleDebugee.
     *
     * @throws VMDisconnectedException          exception
     * @throws InterruptedException             exception
     * @throws IOException                      exception
     * @throws AbsentInformationException       exception
     * @throws IncompatibleThreadStateException exception
     * @throws ClassNotLoadedException          exception
     */
    public void startDebugger() throws VMDisconnectedException, InterruptedException, IOException, AbsentInformationException, IncompatibleThreadStateException, ClassNotLoadedException {
        EventSet eventSet = null;

        while ((eventSet = vm.eventQueue().remove()) != null) {
            for (Event event : eventSet) {

                System.out.println(event.toString());

                if (event instanceof VMDisconnectEvent) {
                    System.out.println("=====End of program. ");
                    return;
                }

                if (event instanceof ClassPrepareEvent) {
                    /**
                     * configuration d'une requête pour placer et activer un point d’arrêt dans la classe nommée
                     * className à la ligne lineNumber.
                     */
                    setBreakPoint(debugClass.getName(), 15);
                }

                if (event instanceof BreakpointEvent) {
                    System.out.println("****************Break****************");
                    for (StepRequest stepR : vm.eventRequestManager().stepRequests()) {
                        stepR.disable();
                    }
                    choseMethode(event);
                }

                if (event instanceof StepEvent) {
                    String pack = ((StepEvent) event).location().declaringType().signature();
                    if ((pack.split("/")[0]).equals("Ldbg")) {

                        event.request().disable();
                        exitProgMethod((StepEvent) event);
                        activeBreakCount((StepEvent) event);
                        uninstallBreakAfterReach(event);
                        choseMethode(event);
                    }
                }
                System.out.println("Debugee output=====");
                InputStreamReader reader = new InputStreamReader(vm.process().getInputStream());
                OutputStreamWriter writer = new OutputStreamWriter(System.out);

                char[] buf = new char[vm.process().getInputStream().available()];

                reader.read(buf);
                writer.write(buf);
                writer.flush();

                vm.resume();
            }
        }
    }
}
