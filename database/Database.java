package greeleysmtpserver.database;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class Database {
  private List<Conversation> conversationList;
  
  public Database() {
    this.conversationList = new ArrayList<Conversation>();
  }
}
