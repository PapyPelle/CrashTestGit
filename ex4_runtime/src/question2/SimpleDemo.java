package question2;
/****************
 Monitoring d'appels de méthodes: exemple de la pile
 Cours: 8INF958
 Auteur: Klaus Havelund
****************/
public class SimpleDemo
{
    public static void main(String[] args)
    {
	Bank b = new Bank();
	//b.withdraw(123, 2000);
	//b.isApproved(123, 2000);
	//b.withdraw(123, 2000);
	//b.isApproved(123, 2000);
	//b.close(123);
	//b.isApproved(123, 2000);
	b.open(123);
	b.withdraw(123, 100);
	//b.open(124);
	//b.open(123);
	b.close(123);
	b.open(123);
	//b.withdraw(123, 100);
	for(int i = 0; i < 2; ++i) {
		if(b.isApproved(123, 1000)) {
			b.withdraw(123, 1000);
		}
	}
	b.close(123);
	b.withdraw(123, 100);
	
	// Error #1
	//b.close(123);
	
	// Error #2
	//b.open(123); b.withdraw(123, 2000); b.close(123);
	
	// Error #3
	//b.open(123); b.isApproved(123, 2000); b.withdraw(456, 2000);
    }
}
