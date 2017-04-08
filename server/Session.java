
package greeleysmtpserver.server;

import greeleysmtpserver.parser.SMTPCommand;
import greeleysmtpserver.responder.SMTPResponse;

/**
 * @author michaelmaitland
 */

public class Session {

    private boolean didSayHelo;
    private boolean didSpecifyMailFrom;
    private boolean isWritingData;
    
    
    
    private void updateSession(SMTPCommand command){
        if(command.getCommandName().equals("HELO")) didSayHelo = true;
        if(command.getCommandName().equals("DATA")) isWritingData = true;
    }
    
    private void updateSession(SMTPResponse response){
        
    }
    public boolean isWritingData(){
        return isWritingData;
    }
    
    
            
}
