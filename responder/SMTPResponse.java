
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
    
    public int getCode(){
        return code;
    }
    
    public String getMessage(){
        return message;
    }
}
