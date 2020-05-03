
//file name: UseServer78.java
//Iyar 5770  update Sivan 5778
//Levian Yehonatan

public class UseSadranServer 
{
    public static void main(String[] arg)
    {
    	Sadran sadran=new Sadran();
        new PassengerServer(sadran);
        new TaxiServer(sadran);
        
    }

}
