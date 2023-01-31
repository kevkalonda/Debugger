package dbg.core;

public class Error {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void reset(){
        message="";
    }
}
