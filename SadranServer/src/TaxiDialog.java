

//file name: Dialog78.java
//Iyar 5770 update Sivan 5778
//Levian Yehonatan
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class TaxiDialog extends Thread // parallel dialogs on the same socket
{

    Socket client;
    TaxiServer myServer;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    TaxiDialogWin myOutput;
    Sadran sadran;

    public TaxiDialog(Socket clientSocket, TaxiServer myServer,Sadran sadran)
    {
    	this.sadran=sadran;
        client = clientSocket;
        this.myServer = myServer;
        try
        {
            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(
                    new InputStreamReader(
                    clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(
                    new BufferedWriter(
                    new OutputStreamWriter(
                    clientSocket.getOutputStream())), true);
        } catch (IOException e)
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
            System.err.println("server:Exception when opening sockets: " + e);
            return;
        }
        myOutput = new TaxiDialogWin("Dialog Win for: " + client.toString(), this);
        start();
    }

    public void run()
    {
    	String driverState;
    	String driverId;
    	String driverArea;
        Taxi taxi=new Taxi(0);
        try
        {
        	
        	driverId=null;
            while (true)
            {
            	if(bufferSocketIn.ready())//if the taxi driver send a message
                {
            		driverState = bufferSocketIn.readLine();//the driver enter "busy" or "available in x "
            		if (driverState == null)
                        break;
                    if (driverState .equals("end"))
                        break;
                    myOutput.printOther(driverState );
                    String[] temp=driverState.split(" "); 	
                    if (driverId==null) {
						bufferSocketOut.println("please enter your id:");
						myOutput.printMe("please enter your id:");
						driverId = bufferSocketIn.readLine();
						myOutput.printOther(driverId);
						
					}
                    taxi = new Taxi(Integer.parseInt(driverId));
					if(temp[0].equals("available"))
                    {
                    	sadran.updateTaxiAccordingArea(Integer.parseInt(temp[3]), taxi);

                    }
                    else if(temp[0].equals("busy"))
                    {
            		sadran.updateTaxiAccordingArea(5,taxi);
                    }
                    else
                    {
                    	bufferSocketOut.println("wrong status,please enter again!");
                    }
            		
                }
            	else if(taxi!=null && taxi.sendTaxi.arrivedEvent())
            	{
            		String address=(String)taxi.sendTaxi.waitEvent();
            		bufferSocketOut.println("Please, go to "+address);
            	}
            	else
            	{
            		continue;
            	}
            }
        } catch (IOException e)
        {
        } finally
        {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
        }

        myOutput.printMe("end of  dialog ");
        myOutput.send.setText("Close");

    }

    void exit()
    {
            try
            {
                client.close();
            } catch (IOException e2)
            {
            }
    }
}
