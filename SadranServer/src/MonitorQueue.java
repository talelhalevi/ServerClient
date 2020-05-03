//     queue for RT (with synchronized & wait)
//Created on : Heshvan 5770
//author     : Levian

public class MonitorQueue extends Queue
{
	MonitorQueue(int depth)
	{
		super(depth);
	}
	
	synchronized void insert(Object aObject)
	{
		while (isFull())
			try
			{
				wait();
			} catch (InterruptedException exce) {}
			
		super.insert(aObject);
		notify();
	}
	
	synchronized Object remove()
	{
		Object item;
		while (isEmpty())
			try
			{
				wait();
			} catch (InterruptedException exce) {}
			
		item=super.remove();
		notify();
		return item;
	}
}
