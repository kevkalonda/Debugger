package dbg.core;

import com.sun.jdi.request.BreakpointRequest;

public class BreakpointRequestCount {
    String className;
    int count;
    int lineNumber;
    int compteur;
    BreakpointRequest breakpointRequest;

    public BreakpointRequestCount() {
        compteur=0;
    }

    public void incrementCompteur(){
        setCompteur(compteur+1);
    }
    public void setClassName(String className) {
        this.className = className;
    }



    public void setBreakpointRequest(BreakpointRequest breakpointRequest) {
        this.breakpointRequest = breakpointRequest;
    }

    public void setCompteur(int compteur) {
        this.compteur = compteur;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getClassName() {
        return className;
    }

    public BreakpointRequest getBreakpointRequest() {
        return breakpointRequest;
    }
}
