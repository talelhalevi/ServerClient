import java.util.HashMap;
import java.util.Map;

public class Sadran 
{
	private MonitorQueue[] taxiAccordingArea=new MonitorQueue[4];//0-darom 1-jerusalem 2-mercaz 3-zafon
	private int[][] distances= {{10,20,30,40},{20,10,20,30},{30,20,10,20},{40,30,20,10}};
	private Map<String,Integer> addresses=new HashMap<String,Integer>();
	public Sadran() 
	{
		super();
		addresses.put( "Nof 65,Arad",new Integer(0));
		addresses.put( "Hatayelet 5,Ashdod",new Integer(0));
		addresses.put( "Haavoda 4,Ashkelon",new Integer(0));
		addresses.put( "Keren Hayesod 34,Jerusalem",new Integer(1));
		addresses.put( "Haneviim,Jerusalem",new Integer(1));
		addresses.put( "Jafo 200,Jerusalem",new Integer(1));
		addresses.put( "Alenbi,Tel Aviv",new Integer(2));
		addresses.put( "Haagana,Hod Hasharon",new Integer(2));
		addresses.put( "Vizman,Kfar Saba",new Integer(2));
		addresses.put( "Hameginim,Zefat",new Integer(3));
		addresses.put( "Habanim,Afula",new Integer(3));
		addresses.put( "Hagalil,Tverya",new Integer(3));
		for(int i=0;i<4;i++)
		{
			taxiAccordingArea[i]=new MonitorQueue(5);
		}
	}
	public void updateTaxiAccordingArea(int driverMessage,Taxi taxi)//if the driverMessage is between 0-3: the driver is available and else: the driver is busy
	{
		if(driverMessage>=4)//if the driver is busy
		{
			for(int i=0;i<4;i++)
			{
				MonitorQueue temp=taxiAccordingArea[i];//copy of the queue in the texi's area
				while(!temp.isEmpty())
				{
					Taxi tempTaxi=(Taxi)temp.remove();
					if(tempTaxi.id!=taxi.id)
					{
						taxiAccordingArea[i].insert(taxiAccordingArea[i].remove());//the taxi enter to the last place in the queue
						
					}
					else//we found the taxi
					{
						taxiAccordingArea[i].remove();
						i=4;
						break;
					}
				}
				
			}
		}
		else if(driverMessage>=0&&driverMessage<=3)//if the driverMessage is between 0-3: the driver is available
		{
			taxiAccordingArea[driverMessage].insert(taxi);
		}
	}
	public String request(String address) 
	{
		int passengerArea=addresses.get(address);//recognize the number of area
		Taxi chosenTaxi=null;
		int chosenArea=passengerArea;
		if(taxiAccordingArea[passengerArea].isEmpty())
		{
			int i=0;
			int minDistance=Integer.MAX_VALUE;
				
			while(i<4)
			{
				if(i!=passengerArea && !taxiAccordingArea[i].isEmpty() && minDistance>distances[passengerArea][i])
				{
					minDistance=distances[passengerArea][i];
					chosenArea=i;
					
				}
				i++;
			}
			if(!taxiAccordingArea[chosenArea].isEmpty())
				chosenTaxi=(Taxi)taxiAccordingArea[chosenArea].remove();
			
		}
		else
		{
			chosenTaxi=(Taxi)taxiAccordingArea[passengerArea].remove();
		}
		if(chosenTaxi!=null)
		{
			chosenTaxi.sendTaxi.sendEvent(address);
			// TODO Auto-generated method stub
			return "The Taxi coming to you in "+distances[passengerArea][chosenArea]+" minutes";
		}
		else
		{
			return "There is no available taxi";
		}
	}
}
	

