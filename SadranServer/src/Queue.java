//  base queue for RT - with synchronized. with Window output
//  Created on : Heshvan 5770
//  author     : Levian
//  File name  : Queue.java 

public class Queue 
{
	private int maxDepth;
	private int head;
	private int tail;
	private int count;  
	private Object data[]; 
	//private QueueWin myWinQueue;

	public Queue (int maxDepth) 
	{
		this.maxDepth=maxDepth;
		data = new Object[maxDepth];
		makeEmpty();
		//myWinQueue=new QueueWin("class Queue");
	}
	
	public void makeEmpty()
	{
		head  = 0;
		tail  = -1;
		count = 0;
	}

	private int next(int no)
	{
		return (no + 1) % (maxDepth);
	}

	synchronized void insert(Object aObject) 
	{
		tail=next(tail);
		data[tail] = aObject;
		count++;
		//myWinQueue.show(maxDepth,head,count);
	}

	synchronized Object remove() 
	{
		Object item = data[head];
		head=next(head);
		count--;
		//myWinQueue.show(maxDepth,head,count);
		return item;
	}

	synchronized boolean isEmpty() 
	{
		return count <= 0;
	}

	synchronized boolean isFull() 
	{
		return count >= maxDepth;
	}

}
