package question2;

import java.util.*;
import ca.uqam.info.logic.LTLStringParser;
import ca.uqam.info.runtime.symbolic.SymbolicWatcher;
 
public aspect Test3
{
 
  SymbolicWatcher w;  
 
  Test3()
  {
    w = new SymbolicWatcher();
    w.setFormula(LTLStringParser.parseFromString("G (( <m1 /return/method> ( [a1 /return/arg/account/value] ( [am1 /return/arg/amount/value] ( [ret /return/retval/value] ( X ([m2 /call/method] ( [a2 /call/arg/account/value] ( [am2 /return/arg/amount/value] ( (((m2) = ({withdraw})) & (!((am2) < ({1000})))) -> ((((m1) = ({isApproved})) & ((am1) = (am2))) & (({ret}) = ({true}))) ) ) )) ) ) ) )) | ( [m2 /call/method] ( ((m2) = ({withdraw})) -> ( [am2 /call/arg/amount/value] ( (am2) < ({1000}) ) ) ) ) )"));
    //w.setFormula(LTLStringParser.parseFromString("G ( X ( [m2 /call/method] ( ((m2) = ({withdraw})) -> ( [am2 /call/arg/amount/value] ( !((am2) > ({1000})) ) ) ) ) )"));
    //w.setFormula(LTLStringParser.parseFromString("G ((<m1 /return/method> ((m1) = ({isApproved}))) | ([m2 /call/method] (!((m2) = ({withdraw})))))"));
    //w.setFormula(LTLStringParser.parseFromString("G ((<m1 /return/method> ([a1 /return/arg/account/value] ([am1 /return/arg/amount/value] ([ret /return/retval/value] (X ([m2 /call/method] ([a2 /call/arg/account/value] ([am2 /return/arg/amount/value] ((((m2) = ({withdraw})) & ((am2) > ({1000}))) -> ((((m1) = ({isApproved})) & ((am1) = (am2))) & (({ret}) = ({true})))))))))))) | (X ([m2 /call/method] (((m2) = ({withdraw})) -> ([am2 /call/arg/amount/value] (!((am2) > ({1000}))))))))"));
    //w.setFormula(LTLStringParser.parseFromString("G ((<m1 /return/method> ([a1 /return/arg/account/value] ([am1 /return/arg/amount/value] ([ret /return/retval/value] (X ([m2 /call/method] ([a2 /call/arg/account/value] ([am2 /return/arg/amount/value] ((((m2) = ({withdraw})) & ((am2) > 1000)) -> ((((m1) = ({isApproved})) & (((am1) = (am2)))) & (({ret}) = ({true})))))))))))) | (X ([m2 /call/method] (((m2) = ({withdraw})) -> ([am2 /call/arg/amount/value] (!((am2) > ({1000}))))))))"));
    //w.setFormula(LTLStringParser.parseFromString("[m1 /call/method] (G (((m1) = ({close})) -> ((!((m1) = ({isApproved}))) U ((m1) = ({open})))))"));
  }
 
 
  pointcut open(Bank _methtarget, int account) :
    call(void open(int)) && target(_methtarget) && args(account);
 
 
  void around(Bank _methtarget, int account) : open(_methtarget, account)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>open</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <account>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </account>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, account);
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
 
  pointcut close(Bank _methtarget, int account) :
    call(void close(int)) && target(_methtarget) && args(account);
 
 
  void around(Bank _methtarget, int account) : close(_methtarget, account)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>close</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <account>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </account>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, account);
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
 
  pointcut withdraw(Bank _methtarget, int account, int amount) :
    call(void withdraw(int, int)) && target(_methtarget) && args(account, amount);
 
 
  void around(Bank _methtarget, int account, int amount) : withdraw(_methtarget, account, amount)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    ficxml="<call>\n";
    ficxml+="  <method>withdraw</method>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <account>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[0] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </account>\n";
    ficxml+="  </arg>\n";
    ficxml+="  <arg>\n";
    ficxml+="    <amount>\n";
    ficxml+="      <value>"+ thisJoinPoint.getArgs()[1] +"</value>\n";
    ficxml+="      <type>int</type>\n";
    ficxml+="    </amount>\n";
    ficxml+="  </arg>\n";
    proceed(_methtarget, account,amount);
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
 
  pointcut isApproved(Bank _methtarget, int account, int amount) :
    call(boolean isApproved(int, int)) && target(_methtarget) && args(account, amount);
 
 
  boolean around(Bank _methtarget, int account, int amount) : isApproved(_methtarget, account, amount)
  {
    //long startDate = System.currentTimeMillis();
    String ficxml;
    boolean ret = proceed(_methtarget, account,amount);
    ficxml="<return> <method>isApproved</method> <arg> <account> <value>" + (account ) + "</value> </account> </arg> <arg> <amount> <value>" + (amount ) + "</value> </amount> </arg> <retval> <value>" + (ret ) + "</value> </retval> </return>";
    ficxml+="</return>\n";
    w.update(ficxml);
 
    switch(w.getOutcome()){
      case FALSE:
        outComeFalse();
        break;
    }
    //System.out.println(ficxml);
    //long endDate = System.currentTimeMillis();
    //System.out.println(endDate - startDate+ \" milli secondes.\n\");
        return ret;
  }
  private void outComeFalse(){
    {
  System.out.println("Cannot withdraw more than 1000 if isApproved did not work");
}
  }
 
}