

//File name Client78.java
//Eiar 5770  update Sivan  5778
//Levian Yehonatan
import java.io.*;
import java.net.*;

class PassengerClient
{

    String SERVERHOST = "LOCALHOST";
//    String SERVERHOST = "";
//    String SERVERHOST = "147.161.23.20";

    int DEFAULT_PORT =1028;
    Socket clientSocket = null;
    BufferedReader bufferSocketIn;
    PrintWriter bufferSocketOut;
    BufferedReader keyBoard;
    PassengerClientWin myOutput;
    String line;

    public void doit()
    {
        try
        {
            // request to server
            clientSocket = new Socket(SERVERHOST, DEFAULT_PORT);

            // Init streams to read/write text in this socket
            bufferSocketIn = new BufferedReader(
                    new InputStreamReader(
                    clientSocket.getInputStream()));
            bufferSocketOut = new PrintWriter(
                    new BufferedWriter(
                    new OutputStreamWriter(
                    clientSocket.getOutputStream())), true);


//  	   Init streams to read text from the keyboard
//	   keyBoard = new BufferedReader(
//	   new InputStreamReader(System.in));


            myOutput = new PassengerClientWin("Passenger Client  ", this);

            // notice about the connection
            myOutput.printMe("Connected to " + clientSocket.getInetAddress() +
                    ":" + clientSocket.getPort());
            line = bufferSocketIn.readLine(); // reads a line from the server, the answer if there is a taxi or not
            if (line == null)  // connection is closed ?  exit
            {
            	myOutput.printMe("Connection closed by the Server.");
            }
            myOutput.printOther(line); // shows it on the screen
               
        } catch (IOException e)
        {
            myOutput.printMe(e.toString());
            System.err.println(e);
        } finally
        {
            try
            {
                if (clientSocket != null)
                {
                    clientSocket.close();
                }
            } catch (IOException e2)
            {
            }
        }
        myOutput.printMe("end of client ");
        myOutput.send.setText("Close");

        System.out.println("end of client ");
    }

    public static void main(String[] args)
    {
        PassengerClient client = new PassengerClient();
        client.doit();
    }
}
