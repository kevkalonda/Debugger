package dbg.core;

import com.sun.jdi.request.BreakpointRequest;

/**
 * BreakpointRequestCount
 */
public class BreakpointRequestCount {
    String className;
    int count;
    int lineNumber;
    int compteur;
    /**
     * {@inheritDoc}
     */
    BreakpointRequest breakpointRequest;

    /**
     * Constructeur
     */
    public BreakpointRequestCount() {
        compteur = 0;
    }

    /**
     * Accesseur {@link String} className
     *
     * @param className {@link String}
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Accesseur {@link BreakpointRequest}
     *
     * @param breakpointRequest {@link BreakpointRequest}
     */
    public void setBreakpointRequest(BreakpointRequest breakpointRequest) {
        this.breakpointRequest = breakpointRequest;
    }

    /**
     * Accesseur lineNumber
     *
     * @return {@link  int}
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Accesseur lineNumber
     *
     * @param lineNumber {@link  int}
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Accesseur lineNumber
     *
     * @return {@link  int}
     */
    public int getCount() {
        return count;
    }

    /**
     * Accesseur lineNumber
     *
     * @param count @return {@link  int}
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Accesseur {@link BreakpointRequest}
     *
     * @return {@link BreakpointRequest}
     */
    public BreakpointRequest getBreakpointRequest() {
        return breakpointRequest;
    }
}
