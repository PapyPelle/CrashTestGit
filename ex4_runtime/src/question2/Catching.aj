package question2;

import java.util.*;
import ca.uqam.info.logic.LTLStringParser;
import ca.uqam.info.runtime.symbolic.SymbolicWatcher;
 
public aspect Catching
{
 
  SymbolicWatcher w;  
 
  Catching()
  {
    w = new SymbolicWatcher();
    w.setFormula(LTLStringParser.parseFromString("([m1 /call/method] ((G (!((m1) = ({close})))) W ((m1) = ({open}))))"));
  }
 
 
  pointcut open(Bank _methtarget, int act) :
    call(void open(int)) && target(_methtarget) && args(act);
 
 
  void around(Bank _methtarget, int act) : open(_methtarget, act)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>open</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <act>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </act>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, act);
    ficxml+="</call>\n";
    w.update(ficxml);
 
    switch(w.getOutcome()){
      case FALSE:
        outComeFalse();
        break;
    }
    //System.out.println(ficxml);
    //long endDate = System.currentTimeMillis();
    //System.out.println(endDate - startDate+ " milli secondes.\n");
 
  }
 
  pointcut close(Bank _methtarget, int act) :
    call(void close(int)) && target(_methtarget) && args(act);
 
 
  void around(Bank _methtarget, int act) : close(_methtarget, act)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>close</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <act>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </act>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, act);
    ficxml+="</call>\n";
    w.update(ficxml);
 
    switch(w.getOutcome()){
      case FALSE:
        outComeFalse();
        break;
    }
    //System.out.println(ficxml);
    //long endDate = System.currentTimeMillis();
    //System.out.println(endDate - startDate+ " milli secondes.\n");
 
  }
 
  pointcut withdraw(Bank _methtarget, int act, int amt) :
    call(void withdraw(int, int)) && target(_methtarget) && args(act, amt);
 
 
  void around(Bank _methtarget, int act, int amt) : withdraw(_methtarget, act, amt)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>withdraw</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <act>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </act>\n";
    ficxml+="  </arg>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <amt>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[1] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </amt>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, act,amt);
    ficxml+="</call>\n";
    w.update(ficxml);
 
    switch(w.getOutcome()){
      case FALSE:
        outComeFalse();
        break;
    }
    //System.out.println(ficxml);
    //long endDate = System.currentTimeMillis();
    //System.out.println(endDate - startDate+ " milli secondes.\n");
 
  }
 
  pointcut isApproved(Bank _methtarget, int act, int amt) :
    call(boolean isApproved(int, int)) && target(_methtarget) && args(act, amt);
 
 
  boolean around(Bank _methtarget, int act, int amt) : isApproved(_methtarget, act, amt)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>isApproved</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <act>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </act>\n";
    ficxml+="  </arg>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <amt>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[1] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </amt>\n";
    ficxml+="  </arg>\n";
    boolean ret = proceed(_methtarget, act,amt);
    ficxml+="</call>\n";
    w.update(ficxml);
 
    switch(w.getOutcome()){
      case FALSE:
        outComeFalse();
        break;
    }
    //System.out.println(ficxml);
    //long endDate = System.currentTimeMillis();
    //System.out.println(endDate - startDate+ " milli secondes.\n");
    return ret;
 
  }
 
  private void outComeFalse()
  {
	  System.out.println("AH SHIT\nHERE WE GO AGAIN");
  }
}
