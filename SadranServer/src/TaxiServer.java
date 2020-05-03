

//file name: Server78.java
//Iyar 5770  update Sivan 5778
//Levian Yehonatan
import java.io.*;
import java.net.*;

class TaxiServer extends Thread 	   //the parallel server
{

    int DEFAULT_PORT = 1029;
    ServerSocket listenSocket;
    Socket clientSockets;
    Sadran sadran;

    public TaxiServer(Sadran sadran)   // constructor of a TCP server
    {
        try
        {
        	this.sadran=sadran;
            listenSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e)    //error
        {
            System.out.println("Problem creating the server-socket");
            System.out.println(e.getMessage());
            System.exit(1);
        }

        System.out.println("Server starts on port " + DEFAULT_PORT);
        start();
    }

	public void run()
    {
        try
        {
            while (true)
            {
                clientSockets = listenSocket.accept();
                new TaxiDialog(clientSockets, this,sadran);
            }

        } catch (IOException e)
        {
            System.out.println("Problem listening server-socket");
            System.exit(1);
        }

        System.out.println("end of server");
    }
}

