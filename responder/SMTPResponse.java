
package greeleysmtpserver.responder;
/**
 * @author michaelmaitland
 */

public class SMTPResponse {

    private int code;
    private String message;
    
    public SMTPResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
    
    public SMTPResponse(){
        //no-args
    }
    
    public int getCode(){
        return code;
    }
    
    public String getMessage(){
        return message;
    }
    
    public void setCode(int code){
        this.code = code;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    @Override
    public String toString(){
        return this.code + " " + this.message;
    }
}
