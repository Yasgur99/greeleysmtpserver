package greeleysmtpserver.responder;

/**
 * @author michaelmaitland
 */

public class Codes {
 public final static int NONSTANDARD_SUCCESS = 200;
 public final static int SYSTEM_STATUS = 211;
 public final static int HELP_MESSAGE = 214;
 public final static int SERVICE_READY = 220;
 public final static int SERVICE_CLOSING_TRANSMISSION = 221;
 public final static int REQUESTED_ACTION_OKAY = 250;
 public final static int USER_NOT_LOCAL = 251;
 public final static int CANT_VRFY = 252;
 public final static int START_MAIL_INPUT = 354;
 public final static int SERVICE_NOT_AVALIABLE = 421;
 public final static int REQUESTED_ACTION_NOT_TAKEN_MAILBOX_UNAVALIABLE = 450;
 public final static int REQUESTED_ACTION_ABORTED_LOCAL_ERROR = 451;
 public final static int REQUESTED_ACTION_NOT_TAKEN_INSUFFICIENT_STORAGE = 452;
 public final static int SYNTAX_ERROR_COMMAND_UNRECOGNIZED = 500;
 public final static int SYNTAX_ERROR_PARAMETERS = 501;
 public final static int COMMAND_NOT_IMPLEMENTED = 502;
 public final static int BAD_SEQUENCE = 503;
 public final static int PARAMETER_NOT_IMPLEMENTED = 504;
 public final static int DOES_NOT_ACCEPT_MAIL = 521;
 public final static int ACCESS_DENIED = 530;
 public final static int REQUESTED_ACTION_ABORTED_EXCEEDED_STORAGE_ALLOCATION = 552;
 public final static int REQUESTED_ACTION_NOT_TAKEN_MAILBOX_NAME_NOT_ALLOWED = 553;
 public final static int TRANSACTION_FAILED = 554;
}
