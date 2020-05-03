

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

class PassengerDialog extends Thread // parallel dialogs on the same socket
{

    Socket client;
    PassengerServer myServer;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    PassengerDialogWin myOutput;
    Sadran sadran;

    public PassengerDialog(Socket clientSocket, PassengerServer myServer,Sadran sadran)
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
        myOutput = new PassengerDialogWin("Dialog Win for: " + client.toString(), this);
        start();
    }


	public void run()
    {
        String line;
        try
        {
                line = bufferSocketIn.readLine();//the request from the passenger client-the address 
                if (line == null)
                    return;
                if (line.equals("end"))
                    return;
                myOutput.printOther(line);
                String answer=sadran.request(line);
                bufferSocketOut.println(answer);//the answer to the PassengerClient
                
            
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
